package uk.ac.ox.it.skossuggester.importers;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.solr.common.SolrDocument;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;


public class SkosFileImporter extends ConfiguredCommand<AppConfiguration> {
    
    private Model model;
    
    public SkosFileImporter() {
        super("skosimport", "Import SKOS file");
    }
    
    @Override
    protected void run(Bootstrap<AppConfiguration> bootstrap, Namespace namespace, AppConfiguration configuration) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void importFile(String location) {
        model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(location);
        if (in == null) {
            throw new IllegalArgumentException("File: " + location + " not found");
        }
        model.read(in, null);
        
        // iterate over Resources having rdf:type schema.org/Topic
        
    }
    
    protected SolrDocument getDocument(Resource res) {
        Model m = ModelFactory.createDefaultModel();
        String nsSkos = "http://www.w3.org/2004/02/skos/core#";
        Property skosPrefLabel = m.createProperty(nsSkos+"prefLabel");
        Property skosAltLabel = m.createProperty(nsSkos+"altLabel");
        
        String prefLabel = res.getProperty(skosPrefLabel).getString();
        List<String> altLabels = new ArrayList<String>();
        
        for (Statement s : res.listProperties(skosAltLabel).toList()) {
            altLabels.add(s.getString());
        }
        
        SolrDocument doc = new SolrDocument();
        doc.addField("prefLabel", prefLabel);
        doc.addField("altLabels", altLabels);
        
        return doc;
    }
}