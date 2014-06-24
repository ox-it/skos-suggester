package uk.ac.ox.it.skossuggester.representations;

import com.google.common.base.Objects;

/**
 * Represents a skos:related concept
 */
public class Related {
    private String label;
    private String uri;

    public Related() {
        
    }
    
    public Related(String label, String uri) {
        this.label = label;
        this.uri = uri;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Related)) return false;
        Related rel = (Related)obj;
        return this.label.equals(rel.label) && this.uri.equals(rel.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label, uri);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("uri", this.uri).toString();
    }
    
}
