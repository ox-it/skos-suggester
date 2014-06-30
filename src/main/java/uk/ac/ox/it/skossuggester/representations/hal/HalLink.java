package uk.ac.ox.it.skossuggester.representations.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author martinfilliau
 */
public class HalLink {
    
    private String value;
    
    public HalLink() {
        
    }
    
    public HalLink(String value) {
        this.value = value;
    }

    @JsonProperty("href")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
