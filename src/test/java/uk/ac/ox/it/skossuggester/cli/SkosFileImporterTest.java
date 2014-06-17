package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import org.apache.solr.common.SolrInputDocument;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class SkosFileImporterTest {

    Model m = ModelFactory.createDefaultModel();
    Property skosPrefLabel = m.createProperty(Skos.PREF_LABEL);
    Property skosAltLabel = m.createProperty(Skos.ALT_LABEL);
    Property skosRelated = m.createProperty(Skos.RELATED);
    
    @Test
    public void testResourceToSolrDocument() {
        Resource r = m.createResource("http://localhost/test");
        
        String[] altLabels = {"REST (Psychotherapy)", "Environmental stimulation, Restricted"};
        
        for (String label : altLabels) {
            r.addProperty(skosAltLabel, label);
        }

        Resource relatedResource = m.createResource("http://localhost/related");
        relatedResource.addProperty(RDFS.label, "Related");
        r.addProperty(skosRelated, relatedResource);
        
        SkosFileImporter sfi = new SkosFileImporter();
        SolrInputDocument doc = sfi.getDocument(r);
        Collection<String> docAltLabels = (Collection) doc.getFieldValues("altLabels");

        assertThat(docAltLabels, containsInAnyOrder(altLabels));
        assertEquals(doc.getFieldValue("uri"), "http://localhost/test");
        assertEquals(doc.getFieldValue("relatedLabels"), "Related");
        assertEquals(doc.getFieldValue("relatedUris"), "http://localhost/related");
        
        String prefLabel = "Restricted environmental stimulation";
        r.addProperty(skosPrefLabel, prefLabel);
        
        doc = sfi.getDocument(r);

        assertEquals(doc.getFieldValue("prefLabel"), prefLabel);        
    }
    
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