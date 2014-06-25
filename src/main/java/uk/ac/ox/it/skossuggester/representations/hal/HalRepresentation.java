package uk.ac.ox.it.skossuggester.representations.hal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martinfilliau
 */
public class HalRepresentation {
    
    private List<HalLink> links;
    private List<Object> embedded;

    public HalRepresentation() {
        this.embedded = new ArrayList<>();
        this.links = new ArrayList<>();
    }
    
    @JsonProperty("_links")
    public List<HalLink> getLinks() {
        return links;
    }

    public void setLinks(List<HalLink> links) {
        this.links = links;
    }

    public void addLink(HalLink link) {
        this.links.add(link);
    }
    
    @JsonProperty("_embedded")
    public List<Object> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(List<Object> embedded) {
        this.embedded = embedded;
    }
    
    public void addEmbedded(Object embed) {
        this.embedded.add(embed);
    }
}
