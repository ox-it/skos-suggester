package uk.ac.ox.it.skossuggester.cli;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author martinfilliau
 */
public class Skos {
    
    public final static String NS = "http://www.w3.org/2004/02/skos/core#";

    public final static String RELATED = NS+"related";
    public final static String ALT_LABEL = NS+"altLabel";
    public final static String PREF_LABEL = NS+"prefLabel";

    /**
     * Get a SolrInputDocument from a Resource
     * @param res Resource to analyse
     * @return SolrInputDocument to be ingested by Solr
     */
    protected static SolrInputDocument getDocument(Resource res) {
        Model m = ModelFactory.createDefaultModel();
        Property skosPrefLabel = m.createProperty(Skos.PREF_LABEL);
        Property skosAltLabel = m.createProperty(Skos.ALT_LABEL);
        Property skosRelated = m.createProperty(Skos.RELATED);

        SolrInputDocument doc = new SolrInputDocument();
        
        doc.addField("uri", res.getURI());
        
        List<String> altLabels = new ArrayList<>();
        
        res.listProperties(skosAltLabel).toList().stream().forEach((s) -> {
            altLabels.add(s.getString());
        });

        doc.addField("altLabels", altLabels);

        Statement prefLabel = res.getProperty(skosPrefLabel);
        if(prefLabel != null) {
            doc.addField("prefLabel", prefLabel.getString());
        }
        
        List<String> relatedLabels = new ArrayList<>();
        List<String> relatedUris = new ArrayList<>();
        
        Resource related;
        Statement relatedStmt;
        for (Statement s : res.listProperties(skosRelated).toList()) {
            related = s.getResource();
            relatedStmt = related.getProperty(RDFS.label);
            if (relatedStmt != null) {
                relatedLabels.add(relatedStmt.getString());
                relatedUris.add(related.getURI());
            }
        }
        
        doc.addField("relatedLabels", relatedLabels);
        doc.addField("relatedUris", relatedUris);
        
        return doc;
    }
}
