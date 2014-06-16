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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;


public class SkosFileImporter extends ConfiguredCommand<AppConfiguration> {
    
    private Model model;
    private HttpSolrServer solr;
    
    public SkosFileImporter() {
        super("skosimport", "Import SKOS file");
    }
    
    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-f", "--file")
                .action(Arguments.store())
                .dest("skosFile")
                .required(true)
                .help("File to be parsed");
        subparser.addArgument("--format")
                .action(Arguments.store())
                .dest("fileFormat")
                .choices("RDF/XML", "N-TRIPLE", "TURTLE", "N3")
                .setDefault("N-TRIPLE")
                .required(false)
                .help("RDF format");
    }
    
    @Override
    protected void run(Bootstrap<AppConfiguration> bootstrap, Namespace namespace, AppConfiguration configuration) throws Exception {
        this.solr = new HttpSolrServer(configuration.getSolrLocation());
        this.importFile(namespace.getString("skosFile"), namespace.getString("fileFormat"));
    }
    
    /**
     * Import a given RDF file to the search index
     * @param location Path of the RDF file
     * @param lang RDF format (RDF/XML, N-TRIPLE, TURTLE or N3)
     */
    private void importFile(String location, String lang) throws SolrServerException, IOException {
        model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(location);
        if (in == null) {
            throw new IllegalArgumentException("File: " + location + " not found");
        }
        model.read(in, null, lang);

        Resource topic = model.createResource("http://schema.org/Topic");
        Collection<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        
        ResIterator it = model.listSubjectsWithProperty(RDF.type, topic);
        
        while (it.hasNext()) {
            documents.add(this.getDocument(it.nextResource()));
        }

        this.solr.add(documents);
        this.solr.commit();
    }
    
    protected SolrInputDocument getDocument(Resource res) {
        Model m = ModelFactory.createDefaultModel();
        String nsSkos = "http://www.w3.org/2004/02/skos/core#";
        Property skosPrefLabel = m.createProperty(nsSkos+"prefLabel");
        Property skosAltLabel = m.createProperty(nsSkos+"altLabel");
        SolrInputDocument doc = new SolrInputDocument();
        
        doc.addField("uri", res.getURI());
        
        List<String> altLabels = new ArrayList<String>();
        
        for (Statement s : res.listProperties(skosAltLabel).toList()) {
            altLabels.add(s.getString());
        }

        doc.addField("altLabels", altLabels);

        Statement prefLabel = res.getProperty(skosPrefLabel);
        if(prefLabel != null) {
            doc.addField("prefLabel", prefLabel.getString());
        }

        return doc;
    }
}