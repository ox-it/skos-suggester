package uk.ac.ox.it.skossuggester.representations.hal;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;

/**
 * Represents a HAL representation
 * @author martinfilliau
 */
public class HalRepresentation {
    
    private HalLinks links;
    private SkosConcepts concepts;

    public HalRepresentation() {
        this.concepts = new SkosConcepts();
        this.links = new HalLinks();
    }
    
    @JsonProperty("_links")
    public HalLinks getLinks() {
        return links;
    }

    public void setLinks(HalLinks links) {
        this.links = links;
    }

    public void setSelfLink(HalLink link) {
        this.links.setSelf(link);
    }
    
    @JsonProperty("_embedded")
    public SkosConcepts getEmbedded() {
        return concepts;
    }

    public void setEmbedded(SkosConcepts embedded) {
        this.concepts = embedded;
    }
    
}
