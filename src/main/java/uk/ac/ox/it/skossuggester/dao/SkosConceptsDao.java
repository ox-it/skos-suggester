package uk.ac.ox.it.skossuggester.dao;

import com.google.common.base.Optional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryResponseParser;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;
import uk.ac.ox.it.skossuggester.resources.Get;

/**
 * Encapsulates all queries to Solr
 * @author martinfilliau
 */
public class SkosConceptsDao {
 
    private final SolrServer solr;
    
    /**
     * Constructor for SkosConceptsDao
     * @param solr Instance of SolrServer (e.g. HttpSolrServer)
     */
    public SkosConceptsDao(SolrServer solr) {
        this.solr = solr;
    }
    
    /**
     * Get documents by their unique IDs
     * @param uris list of URIs
     * @return SkosConcepts or absent
     */
    public Optional<SkosConcepts> get(List<String> uris) {
        SolrQuery q = new SolrQuery();
        q.setRequestHandler("/get");
        for(String uri : uris) {
            q.add("id", uri);        
        }
        Optional<QueryResponse> rsp = this.doQuery(q);
        
        // Solr returns a different response if there is only one document...
        if (uris.size() == 1) {
            if (rsp.isPresent()) {
                SolrDocument out = (SolrDocument)rsp.get().getResponse().get("doc");
                if (out != null) {
                    SkosConcepts concepts = new SkosConcepts();
                    concepts.addConcept(SkosConcept.fromSolr(out));
                    return Optional.of(concepts);
                }
            }
        } else {
            return responseToConcepts(rsp);
        }
        return Optional.absent();
    }
    
    /**
     * Search for documents by a query string
     * Use the "suggest" handler which provides a light response
     * @param query string to search
     * @param start first document to retrieve
     * @param count number of documents to retrieve
     * @return SkosConcepts or absent
     */
    public Optional<SkosConcepts> suggest(String query, Integer start, Integer count) {
        SolrQuery q = new SolrQuery();
        q.setQuery(query);
        q.setRequestHandler("/suggest");
        q.setStart(start);
        q.setRows(count);
        Optional<QueryResponse> rsp = this.doQuery(q);
        return responseToConcepts(rsp);
    }
    
    /**
     * Search for documents by a query string
     * @param query string to search
     * @param start first document to retrieve
     * @param count number of documents to retrieve
     * @return SkosConcepts or absent
     */
    public Optional<SkosConcepts> search(String query, Integer start, Integer count) {
        SolrQuery q = new SolrQuery();
        q.setQuery(query);
        q.set("defType", "edismax");
        // boosting per field
        q.set("qf", "prefLabel^5 altLabels^2 relatedLabels^1");
        q.setStart(start);
        q.setRows(count);
        Optional<QueryResponse> rsp = this.doQuery(q);
        return responseToConcepts(rsp);
    }
    
    /**
     * Do a request to Solr
     * @param query SolrQuery
     * @return QueryResponse object or absent
     */
    private Optional<QueryResponse> doQuery(SolrQuery query) {
        QueryRequest req = new QueryRequest(query);
        req.setResponseParser(new BinaryResponseParser());
        QueryResponse rsp;
        try {
            rsp = req.process(solr);
            return Optional.of(rsp);
        } catch (SolrServerException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.absent();
    }

    /**
     * Transforms a SolrDocumentList into a domain object SkosConcepts
     * @param response QueryResponse (response from Solr)
     * @return SkosConcepts or absent
     */
    private Optional<SkosConcepts> responseToConcepts(Optional<QueryResponse> response) {
        if(response.isPresent()) {
            SolrDocumentList out = response.get().getResults();
            if (out != null) {
                return Optional.of(SkosConcepts.fromSolr(out));
            }
        }
        return Optional.absent();

    }
}
