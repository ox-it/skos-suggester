package uk.ac.ox.it.skossuggester.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 *
 * @author martinfilliau
 */
public class HttpSolrFactory {
    
    private String solrLocation;

    @JsonProperty("url")
    public String getSolrLocation() {
        return solrLocation;
    }

    @JsonProperty("url")
    public void setSolrLocation(String solrLocation) {
        this.solrLocation = solrLocation;
    }
    
    public boolean isValid() {
        return this.solrLocation != null;
    }
    
    public HttpSolrServer build(Environment environment) throws Exception {
        if (this.isValid()) {
            final HttpSolrServer solr = new HttpSolrServer(this.solrLocation);
            solr.setConnectionTimeout(1000);    // 1 second
            solr.setSoTimeout(1000);    // 1 second
            if (environment != null) {
                environment.lifecycle().manage(new Managed() {
                    @Override
                    public void start() throws Exception {
                    }

                    @Override
                    public void stop() throws Exception {
                        solr.shutdown();
                    }
                });                
            }
            return solr;
        } else {
            throw new Exception("Missing parameters to instantiate HttpSolrServer");
        }
    }
}
