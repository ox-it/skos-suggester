package uk.ac.ox.it.skossuggester.configuration;

import io.dropwizard.Configuration;


public class AppConfiguration extends Configuration {
    
    private String solrLocation = new String();

    public String getSolrLocation() {
        return solrLocation;
    }

    public void setSolrLocation(String solrLocation) {
        this.solrLocation = solrLocation;
    }
}
