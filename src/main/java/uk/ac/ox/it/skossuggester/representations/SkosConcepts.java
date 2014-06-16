package uk.ac.ox.it.skossuggester.representations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


public class SkosConcepts {
    
    private List<SkosConcept> concepts;

    public List<SkosConcept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<SkosConcept> concepts) {
        this.concepts = concepts;
    }
    
    public static SkosConcepts fromSolr(SolrDocumentList docs) {
        SkosConcepts concepts = new SkosConcepts();
        List<SkosConcept> skos = new ArrayList<SkosConcept>();
        Iterator<SolrDocument> it = docs.iterator();
        SolrDocument doc;
        while(it.hasNext()) {
            doc = it.next();
            skos.add(SkosConcept.fromSolr(doc));
        }
        
        concepts.setConcepts(skos);
        return concepts;
    }
}