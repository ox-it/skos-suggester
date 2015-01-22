package uk.ac.ox.it.skossuggester;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;
import uk.ac.ox.it.skossuggester.health.SolrHealth;
import uk.ac.ox.it.skossuggester.cli.SkosFileImporter;
import uk.ac.ox.it.skossuggester.cli.TdbImporter;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.jerseyutils.JsonIllegalArgumentExceptionMapper;
import uk.ac.ox.it.skossuggester.resources.Get;
import uk.ac.ox.it.skossuggester.resources.Search;
import uk.ac.ox.it.skossuggester.resources.Suggest;

public class SkosSuggesterApplication extends Application<AppConfiguration>{

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addCommand(new SkosFileImporter());
        bootstrap.addCommand(new TdbImporter());
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        // CORS filter configuration
        FilterRegistration.Dynamic cors = environment.servlets().addFilter("cors", CrossOriginFilter.class);
        cors.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "false");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET");
        
        final HttpSolrServer solr = new HttpSolrServer(configuration.getSolrLocation());
        solr.setConnectionTimeout(1000);    // 1 second
        solr.setSoTimeout(1000);    // 1 second
        final SkosConceptsDao dao = new SkosConceptsDao(solr);
        final Suggest suggest = new Suggest(dao);
        final Search search = new Search(dao);
        final Get get = new Get(dao);
        environment.jersey().register(suggest);
        environment.jersey().register(search);
        environment.jersey().register(get);
        final SolrHealth solrHealth = new SolrHealth(solr);
        environment.healthChecks().register("solr", solrHealth);
        environment.jersey().register(new JsonIllegalArgumentExceptionMapper());
    }
    
    public static void main(String[] args) throws Exception {
        new SkosSuggesterApplication().run(args);
    }
}