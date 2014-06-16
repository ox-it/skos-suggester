package uk.ac.ox.it.skossuggester.representations;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author martinfilliau
 */
public class SkosConceptsTest {
    
    @Test
    public void serializesToJSON() {
        
    }
    
    @Test
    public void deserializesFromJSON() {
        
    }
    
    @Test
    public void solrDocsToConcept() {
        SolrDocumentList solrDocuments = new SolrDocumentList();
        SolrDocument doc1 = new SolrDocument();
        doc1.setField("uri", "http://uri.1");
        doc1.setField("prefLabel", "one");
        solrDocuments.add(doc1);
        SolrDocument doc2 = new SolrDocument();
        doc2.setField("uri", "http://uri.2");
        doc2.setField("prefLabel", "two");
        solrDocuments.add(doc2);
        
        SkosConcepts concepts = SkosConcepts.fromSolr(solrDocuments);
        assertEquals(concepts.getConcepts().size(), 2);
    }
}
