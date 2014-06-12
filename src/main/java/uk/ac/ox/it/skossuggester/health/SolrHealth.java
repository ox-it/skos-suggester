package uk.ac.ox.it.skossuggester.health;

import com.codahale.metrics.health.HealthCheck;


public class SolrHealth extends HealthCheck{

    @Override
    protected Result check() throws Exception {
        return Result.unhealthy("TODO");
    }
    
}