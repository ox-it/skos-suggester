package uk.ac.ox.it.skossuggester;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;
import uk.ac.ox.it.skossuggester.health.SolrHealth;
import uk.ac.ox.it.skossuggester.resources.Get;
import uk.ac.ox.it.skossuggester.resources.Search;
import uk.ac.ox.it.skossuggester.resources.Suggest;

public class SkosSuggesterApplication extends Application<AppConfiguration>{

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // nothing yet
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        final Suggest suggest = new Suggest();
        final Search search = new Search();
        final Get get = new Get();
        environment.jersey().register(suggest);
        environment.jersey().register(search);
        environment.jersey().register(get);
        final SolrHealth solrHealth = new SolrHealth();
        environment.healthChecks().register("solr", solrHealth);
    }
    
    public static void main(String[] args) throws Exception {
        new SkosSuggesterApplication().run(args);
    }
}