package uk.ac.ox.it.skossuggester.representations;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.solr.common.SolrDocument;

public class SkosConcept {
    
    private String uri;
    private String prefLabel;
    private List<String> altLabels;
    private List<Related> related;

    public SkosConcept() {
        this.altLabels = new ArrayList<>();
        this.related = new ArrayList<>();
    }
    
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(String prefLabel) {
        this.prefLabel = prefLabel;
    }

    public List<String> getAltLabels() {
        return altLabels;
    }

    public void setAltLabels(List<String> altLabels) {
        this.altLabels = altLabels;
    }

    public List<Related> getRelated() {
        return related;
    }

    public void setRelated(List<Related> related) {
        this.related = related;
    }
        
    public static SkosConcept fromSolr(SolrDocument doc) {
        SkosConcept skos = new SkosConcept();
        skos.setUri((String) doc.getFieldValue("uri"));
        if(doc.containsKey("prefLabel")) {
            skos.setPrefLabel((String) doc.getFieldValue("prefLabel"));
        }

        List<String> altLabels = new ArrayList<String>();
        if(doc.containsKey("altLabels")) {
            for(Object o : doc.getFieldValues("altLabels")) {
                altLabels.add(o.toString());
            }
            skos.setAltLabels(altLabels);            
        }

        if(doc.containsKey("relatedLabels") && doc.containsKey("relatedUris")) {
            List<Object> relatedLabels = new ArrayList(doc.getFieldValues("relatedLabels"));
            List<Object> relatedUris = new ArrayList(doc.getFieldValues("relatedUris"));

            List<Related> rels = new ArrayList<Related>();
            for (int i = 0; i < relatedLabels.size(); i++) {
                String label = (String)relatedLabels.get(i);
                String uri = (String)relatedUris.get(i);
                rels.add(new Related(label, uri));
            }
            skos.setRelated(rels);
        }
        return skos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SkosConcept)) return false;
        SkosConcept c = (SkosConcept)obj;
        return this.uri.equals(c.uri)
                && this.prefLabel.equals(c.prefLabel)
                && this.altLabels.equals(c.altLabels)
                && this.related.equals(c.related);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 41)
                .append(uri)
                .append(prefLabel)
                .append(altLabels)
                .append(related)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SkosConcept<" + this.uri + ">";
    }

}