package uk.ac.ox.it.skossuggester.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.apache.solr.client.solrj.SolrServer;


public class AppConfiguration extends Configuration {

    private SolrCloudFactory solrCloud = new SolrCloudFactory();
    
    private HttpSolrFactory httpSolr = new HttpSolrFactory();

    @JsonProperty("solrCloud")
    public SolrCloudFactory getSolrCloud() {
        return solrCloud;
    }

    @JsonProperty("solrCloud")
    public void setSolrCloud(SolrCloudFactory solrCloud) {
        this.solrCloud = solrCloud;
    }

    @JsonProperty("solrHttp")
    public HttpSolrFactory getHttpSolr() {
        return httpSolr;
    }

    @JsonProperty("solrHttp")
    public void setHttpSolr(HttpSolrFactory httpSolr) {
        this.httpSolr = httpSolr;
    }
    
    /**
     * Get a SolrServer depending on the configuration
     * @param environment Dropwizard Environment
     * @return SolrServer
     * @throws Exception 
     */
    @JsonIgnore
    public SolrServer getSolrServer(Environment environment) throws Exception {
        if (this.httpSolr.isValid() && this.solrCloud.isValid()) {
            throw new Exception("You need to specify either solrCloud or solrHttp, not both.");
        } else if (this.httpSolr.isValid()) {
            return this.httpSolr.build(environment);
        } else if (this.solrCloud.isValid()) {
            return this.solrCloud.build(environment);
        } else {
            throw new Exception("You need to specify either solrCloud or solrHttp");
        }
    }
}
