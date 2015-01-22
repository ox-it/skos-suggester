package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;

/**
 * Import SKOS concepts from an RDF file
 * Easier to use than TdbImporter as it does not need
 * an intermediary Jena TDB store, but might reach memory limits
 * @author martinfilliau
 */
public class SkosFileImporter extends ConfiguredCommand<AppConfiguration> {
    
    public SkosFileImporter() {
        super("skosimport", "Import SKOS file");
    }
    
    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-f", "--file")
                .action(Arguments.store())
                .type(Arguments.fileType()
                        .acceptSystemIn()
                        .verifyCanRead()
                        .verifyIsFile())
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
        Collection<SolrInputDocument> documents = this.getDocsFromFile((File)namespace.get("skosFile"),
                namespace.getString("fileFormat"));
        solr.add(documents);
        solr.commit();   
    }
    
    /**
     * Import a given RDF file to the search index
     * @param file RDF file
     * @param lang RDF format (RDF/XML, N-TRIPLE, TURTLE or N3)
     * @return collection of SolrInputDocument
     * @throws java.io.FileNotFoundException
     */
    protected Collection<SolrInputDocument> getDocsFromFile(File file, String lang) throws FileNotFoundException {
        Model model = ModelFactory.createDefaultModel();
        InputStream in = new FileInputStream(file);
        model.read(in, null, lang);
        return this.getDocsFromModel(model);
    }
    
    /**
     * Import a Model
     * @param m Jena Model
     * @return collection of SolrInputDocument 
     */
    protected Collection<SolrInputDocument> getDocsFromModel(Model m) {
        Resource topic = m.createResource("http://schema.org/Topic");       // TODO URI should not be hard-coded, #reusability
        Collection<SolrInputDocument> documents = new ArrayList<>();
        
        ResIterator it = m.listSubjectsWithProperty(RDF.type, topic);
        
        while (it.hasNext()) {
            documents.add(Skos.getDocument(it.nextResource()));
        }
        return documents;     
    }
}