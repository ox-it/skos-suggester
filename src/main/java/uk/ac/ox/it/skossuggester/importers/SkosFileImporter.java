package uk.ac.ox.it.skossuggester.importers;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;


public class SkosFileImporter extends ConfiguredCommand<AppConfiguration> {

    public SkosFileImporter() {
        super("skosimport", "Import SKOS file");
    }
    
    @Override
    protected void run(Bootstrap<AppConfiguration> bootstrap, Namespace namespace, AppConfiguration configuration) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}