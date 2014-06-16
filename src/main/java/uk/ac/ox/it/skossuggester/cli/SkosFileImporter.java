package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
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
        HttpSolrServer solr = new HttpSolrServer(configuration.getSolrLocation());
        Collection<SolrInputDocument> documents = this.getDocsFromFile(namespace.getString("skosFile"),
                namespace.getString("fileFormat"));
        solr.add(documents);
        solr.commit();   
    }
    
    /**
     * Import a given RDF file to the search index
     * @param location Path of the RDF file
     * @param lang RDF format (RDF/XML, N-TRIPLE, TURTLE or N3)
     * @return collection of SolrInputDocument
     */
    protected Collection<SolrInputDocument> getDocsFromFile(String location, String lang) {
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(location);
        if (in == null) {
            throw new IllegalArgumentException("File: " + location + " not found");
        }
        model.read(in, null, lang);
        return this.getDocsFromModel(model);
    }
    
    /**
     * Import a Model
     * @param m Jena Model
     * @return collection of SolrInputDocument
     * @throws SolrServerException
     * @throws IOException 
     */
    protected Collection<SolrInputDocument> getDocsFromModel(Model m) {
        Resource topic = m.createResource("http://schema.org/Topic");
        Collection<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        
        ResIterator it = m.listSubjectsWithProperty(RDF.type, topic);
        
        while (it.hasNext()) {
            documents.add(this.getDocument(it.nextResource()));
        }
        return documents;     
    }
    
    /**
     * Get a SolrInputDocument from a Resource
     * @param res Resource to analyse
     * @return SolrInputDocument to be ingested by Solr
     */
    protected SolrInputDocument getDocument(Resource res) {
        Model m = ModelFactory.createDefaultModel();
        Property skosPrefLabel = m.createProperty(Skos.PREF_LABEL);
        Property skosAltLabel = m.createProperty(Skos.ALT_LABEL);
        Property skosRelated = m.createProperty(Skos.RELATED);

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
        
        List<String> relatedLabels = new ArrayList<String>();
        List<String> relatedUris = new ArrayList<String>();
        
        Resource related;
        Statement relatedStmt;
        for (Statement s : res.listProperties(skosRelated).toList()) {
            related = s.getResource();
            relatedStmt = related.getProperty(RDFS.label);
            if (relatedStmt != null) {
                relatedLabels.add(relatedStmt.getString());
                relatedUris.add(related.getURI());
            }
        }

        doc.addField("relatedLabels", relatedLabels);
        doc.addField("relatedUris", relatedUris);
        
        return doc;
    }
}