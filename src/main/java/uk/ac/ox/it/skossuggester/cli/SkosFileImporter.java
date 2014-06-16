package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;


public class SkosFileImporter extends ConfiguredCommand<AppConfiguration> {
    
    private Model model;
    
    public SkosFileImporter() {
        super("skosimport", "Import SKOS file");
    }
    
    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-f", "--file")
                 .action(Arguments.store())
                 .dest("skosFile")
                 .help("File to be parsed");
    }
    
    @Override
    protected void run(Bootstrap<AppConfiguration> bootstrap, Namespace namespace, AppConfiguration configuration) throws Exception {
        this.importFile(namespace.getString("skosFile"));
        
    }
    
    private void importFile(String location) {
        model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(location);
        if (in == null) {
            throw new IllegalArgumentException("File: " + location + " not found");
        }
        model.read(in, null, "N-TRIPLE");

        Resource topic = model.createResource("http://schema.org/Topic");
        Collection<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        
        ResIterator it = model.listSubjectsWithProperty(RDF.type, topic);
        
        while (it.hasNext()) {
            documents.add(this.getDocument(it.nextResource()));
        }
        
        // iterate over Resources having rdf:type schema.org/Topic
        
    }
    
    protected SolrInputDocument getDocument(Resource res) {
        Model m = ModelFactory.createDefaultModel();
        String nsSkos = "http://www.w3.org/2004/02/skos/core#";
        Property skosPrefLabel = m.createProperty(nsSkos+"prefLabel");
        Property skosAltLabel = m.createProperty(nsSkos+"altLabel");
        
        String prefLabel = res.getProperty(skosPrefLabel).getString();
        List<String> altLabels = new ArrayList<String>();
        
        for (Statement s : res.listProperties(skosAltLabel).toList()) {
            altLabels.add(s.getString());
        }
        
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("prefLabel", prefLabel);
        doc.addField("altLabels", altLabels);

        return doc;
    }
}