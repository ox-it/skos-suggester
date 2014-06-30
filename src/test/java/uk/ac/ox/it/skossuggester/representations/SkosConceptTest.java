package uk.ac.ox.it.skossuggester.representations;

import com.fasterxml.jackson.databind.ObjectMapper;
import static io.dropwizard.testing.FixtureHelpers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.common.SolrDocument;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author martinfilliau
 */
public class SkosConceptTest {
    
    /**
     * Test concept
     * @return SkosConcept
     */
    private SkosConcept getConcept() {
        final SkosConcept concept = new SkosConcept();
        concept.setUri("http://id.worldcat.org/fast/887935");
        concept.setPrefLabel("Data encryption (Computer science)");
        concept.addAltLabel("Encryption of data (Computer science)");
        concept.addAltLabel("Data encoding (Computer science)");
        concept.addRelated(new Related("Computer security", "http://id.worldcat.org/fast/872484"));
        return concept;
    }
    
    @Test
    public void serializesToJSON() throws IOException {
        SkosConcept concept = this.getConcept();
        ObjectMapper mapper = new ObjectMapper();
        String jsonConcept = mapper.writeValueAsString(concept);
        assertThat("a SkosConcept can be serialized to JSON",
               jsonConcept,
               is(equalTo(fixture("fixtures/concept.json"))));
    }
    
    @Test
    public void deserializesFromJSON() throws IOException {
        SkosConcept concept = this.getConcept();
        
        ObjectMapper mapper = new ObjectMapper();
        SkosConcept deserialized = mapper.readValue(fixture("fixtures/concept.json"), SkosConcept.class);
        assertEquals(deserialized, concept);
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
