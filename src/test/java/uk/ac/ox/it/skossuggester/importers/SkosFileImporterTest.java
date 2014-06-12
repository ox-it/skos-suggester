package uk.ac.ox.it.skossuggester.importers;

import com.hp.hpl.jena.rdf.model.Resource;
import org.apache.solr.common.SolrDocument;
import org.junit.Test;

public class SkosFileImporterTest {
    
    @Test
    public void testResourceToSolrDocument() {
        SkosFileImporter sfi = new SkosFileImporter();
        SolrDocument doc = sfi.getDocument(null);
        
    }
}