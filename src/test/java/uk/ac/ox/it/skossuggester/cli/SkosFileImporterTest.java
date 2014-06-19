package uk.ac.ox.it.skossuggester.cli;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import org.apache.solr.common.SolrInputDocument;
import static org.junit.Assert.*;
import org.junit.Test;

public class SkosFileImporterTest {
    
    @Test
    public void testSkosToDocuments() throws IOException {
        SkosFileImporter sfi = new SkosFileImporter();
        // using sample file in src/test/resources/skos
        URL url = this.getClass().getResource("/skos/computer_topics.nt");
        File f = new File(url.getFile());
        Collection<SolrInputDocument> docs = sfi.getDocsFromFile(f, "N-TRIPLE");
        assertEquals(docs.size(), 4);
    }
}