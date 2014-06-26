package uk.ac.ox.it.skossuggester.representations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.base.Objects;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


public class SkosConcepts {
    
    private List<SkosConcept> concepts;

    public SkosConcepts() {
        this.concepts = new ArrayList<>();
    }
    
    public SkosConcepts(List<SkosConcept> concepts) {
        this.concepts = concepts;
    }

    /**
     * Add a SkosConcept to the list of concepts
     * @param concept SkosConcept
     */
    public void addConcept(SkosConcept concept) {
        this.concepts.add(concept);
    }
    
    public List<SkosConcept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<SkosConcept> concepts) {
        this.concepts = concepts;
    }
    
    public static SkosConcepts fromSolr(SolrDocumentList docs) {
        SkosConcepts concepts = new SkosConcepts();
        Iterator<SolrDocument> it = docs.iterator();
        SolrDocument doc;
        while(it.hasNext()) {
            doc = it.next();
            concepts.addConcept(SkosConcept.fromSolr(doc));
        }
        return concepts;
    }
        
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SkosConcepts)) return false;
        SkosConcepts c = (SkosConcepts)obj;
        return this.concepts.equals(c.concepts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(concepts);
    }
}