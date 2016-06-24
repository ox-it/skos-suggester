package uk.ac.ox.it.skossuggester.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.apache.solr.client.solrj.impl.CloudSolrServer;

/**
 *
 * @author martinfilliau
 */
public class SolrCloudFactory {

    private String zookeperLocation;
    
    private String collectionName;

    @JsonProperty
    public String getZookeperLocation() {
        return zookeperLocation;
    }

    @JsonProperty
    public void setZookeperLocation(String zookeperLocation) {
        this.zookeperLocation = zookeperLocation;
    }

    @JsonProperty
    public String getCollectionName() {
        return collectionName;
    }

    @JsonProperty
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
    
    /**
     * Check if the configuration is valid
     * @return true if configuration is ok else false
     */
    public boolean isValid() {
        return zookeperLocation != null && collectionName != null;
    }
    
    public CloudSolrServer build(Environment environment) throws Exception {
        if (this.isValid()) {
            final CloudSolrServer solrCloud = new CloudSolrServer(this.zookeperLocation);
            solrCloud.setDefaultCollection(this.collectionName);
            if (environment != null) {
                environment.lifecycle().manage(new Managed() {
                    @Override
                    public void start() throws Exception {
                        solrCloud.connect();
                    }

                    @Override
                    public void stop() throws Exception {
                        solrCloud.shutdown();
                    }
                });                
            }
            return solrCloud;
        } else {
            throw new Exception("Missing parameters to initiate SolrCloud client");
        }
    }
}
