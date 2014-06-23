package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.Collection;
import org.apache.solr.common.SolrInputDocument;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author martinfilliau
 */
public class SkosTest {
        
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
        
        SolrInputDocument doc = Skos.getDocument(r);
        Collection<String> docAltLabels = (Collection) doc.getFieldValues("altLabels");

        assertThat(docAltLabels, containsInAnyOrder(altLabels));
        assertEquals(doc.getFieldValue("uri"), "http://localhost/test");
        assertEquals(doc.getFieldValue("relatedLabels"), "Related");
        assertEquals(doc.getFieldValue("relatedUris"), "http://localhost/related");
        
        String prefLabel = "Restricted environmental stimulation";
        r.addProperty(skosPrefLabel, prefLabel);
        
        doc = Skos.getDocument(r);

        assertEquals(doc.getFieldValue("prefLabel"), prefLabel);        
    }
}
