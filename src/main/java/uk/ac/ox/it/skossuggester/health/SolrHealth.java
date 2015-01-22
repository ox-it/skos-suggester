package uk.ac.ox.it.skossuggester.health;

import com.codahale.metrics.health.HealthCheck;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.SolrPingResponse;

/**
 * Health check
 * @author martinfilliau
 */
public class SolrHealth extends HealthCheck{

    private final SolrServer solr;
    
    public SolrHealth(SolrServer solr) {
        this.solr = solr;
    }
    
    @Override
    protected Result check() throws Exception {
        SolrPingResponse response = this.solr.ping();
        // 0 means OK
        if (response.getStatus() == 0) {
            return Result.healthy();
        } else {
            return Result.unhealthy(response.toString());
        }   
    }
}