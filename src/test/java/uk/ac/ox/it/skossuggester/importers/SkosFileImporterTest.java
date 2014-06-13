package uk.ac.ox.it.skossuggester.importers;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.List;
import org.apache.solr.common.SolrDocument;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SkosFileImporterTest {
    
    @Test
    public void testResourceToSolrDocument() {
        Model m = ModelFactory.createDefaultModel();
        String nsSkos = "http://www.w3.org/2004/02/skos/core#";
        Property skosPrefLabel = m.createProperty(nsSkos+"prefLabel");
        Property skosAltLabel = m.createProperty(nsSkos+"altLabel");

        Resource r = m.createResource();
        String prefLabel = "Restricted environmental stimulation";
        r.addProperty(skosPrefLabel, prefLabel);
        
        String[] altLabels = {"REST (Psychotherapy)", "Environmental stimulation, Restricted"};
        
        for (String label : altLabels) {
            r.addProperty(skosAltLabel, label);
        }
        
        SkosFileImporter sfi = new SkosFileImporter();
        SolrDocument doc = sfi.getDocument(r);
        List<String> docAltLabels = (List<String>) doc.get("altLabels");
        
        assertEquals(doc.get("prefLabel"), prefLabel);
        assertThat(docAltLabels, containsInAnyOrder(altLabels));
    }
}