package uk.ac.ox.it.skossuggester.representations;

import org.apache.solr.common.SolrDocument;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author martinfilliau
 */
public class SkosConceptTest {
    
    @Test
    public void serializesToJSON() {
        
    }
    
    @Test
    public void deserializesFromJSON() {
        
    }
    
    @Test
    public void solrDocToConcept() {
        SolrDocument doc = new SolrDocument();
        doc.setField("uri", "http://uri");
        String[] altLabels = new String[]{"a", "b"};
        doc.setField("altLabels", altLabels);
        SkosConcept skos = SkosConcept.fromSolr(doc);
        assertEquals(skos.getUri(), "http://uri");
        assertThat(skos.getAltLabels(), containsInAnyOrder(altLabels));
        
        doc.setField("prefLabel", "prefLabel");
        skos = SkosConcept.fromSolr(doc);
        assertEquals(skos.getPrefLabel(), "prefLabel");

        String[] relatedLabels = new String[]{"relA","relB"};
        String[] relatedUris = new String[]{"http://rel.a", "http://rel.b"};
        doc.setField("relatedLabels", relatedLabels);
        doc.setField("relatedUris", relatedUris);
        skos = SkosConcept.fromSolr(doc);
        Related[] rels = new Related[]{new Related("relA", "http://rel.a"),
                                        new Related("relB", "http://rel.b")};
        assertThat(skos.getRelated(), containsInAnyOrder(rels));
    }
}
