package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import java.io.File;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;

/**
 *
 * @author martinfilliau
 */
public class TdbImporter extends ConfiguredCommand<AppConfiguration> {

    public TdbImporter() {
        super("tdbimport", "Import from TDB");
    }
    
    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-d")
                .action(Arguments.store())
                .type(Arguments.fileType()
                        .verifyCanRead()
                        .verifyIsDirectory())
                .dest("tdbDirectory")
                .required(true)
                .help("TDB directory");
    }
    
    @Override
    protected void run(Bootstrap<AppConfiguration> btstrp, Namespace namespace, AppConfiguration configuration) throws Exception {
        ConcurrentUpdateSolrServer solr = new ConcurrentUpdateSolrServer(configuration.getSolrLocation(), 100, 3);
        File tdbDirectory = (File)namespace.get("tdbDirectory");
        Dataset dataset = TDBFactory.createDataset(tdbDirectory.getAbsolutePath());
        Model tdb = dataset.getDefaultModel();
        Resource topic = tdb.createResource("http://schema.org/Topic");
        ResIterator it = tdb.listSubjectsWithProperty(RDF.type, topic);
        
        while (it.hasNext()) {
            solr.add(Skos.getDocument(it.nextResource()));
        }
        
        solr.commit();
        tdb.close();
        dataset.close();
    }
    
}
