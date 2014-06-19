package uk.ac.ox.it.skossuggester.dao;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryResponseParser;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;
import uk.ac.ox.it.skossuggester.resources.Get;

/**
 *
 * @author martinfilliau
 */
public class SkosConceptsDao {
 
    private final HttpSolrServer solr;
    
    public SkosConceptsDao(HttpSolrServer solr) {
        this.solr = solr;
    }
    
    /**
     * Get a document by its unique ID
     * @param uri 
     * @return 
     */
    public Optional<SkosConcept> get(String uri) {
        SolrQuery q = new SolrQuery();
        q.setRequestHandler("/get");
        q.set("id", uri);
        Optional<QueryResponse> rsp = this.doQuery(q);
        if (rsp.isPresent()) {
            SolrDocument out = (SolrDocument)rsp.get().getResponse().get("doc");
            if (out != null) {
                return Optional.of(SkosConcept.fromSolr(out));
            }
        }
        return Optional.absent();
    }
    
    /**
     * Search for documents by a query string
     * @param query string to search
     * @return SkosConcepts
     */
    public Optional<SkosConcepts> suggest(String query) {
        SolrQuery q = new SolrQuery();
        q.setQuery(query);
        q.setRequestHandler("/suggest");
        Optional<QueryResponse> rsp = this.doQuery(q);
        if(rsp.isPresent()) {
            SolrDocumentList out = rsp.get().getResults();
            if (out != null) {
                return Optional.of(SkosConcepts.fromSolr(out));
            }
        }
        return Optional.absent();
    }
    
    /**
     * Search for documents by a query string
     * @param query string to search
     * @param start first document to retrieve
     * @param count number of documents to retrieve
     * @return SkosConcepts
     */
    public Optional<SkosConcepts> search(String query, Integer start, Integer count) {
        SolrQuery q = new SolrQuery();
        q.setQuery(query);
        q.setStart(start);
        q.setRows(count);
        Optional<QueryResponse> rsp = this.doQuery(q);
        if(rsp.isPresent()) {
            SolrDocumentList out = rsp.get().getResults();
            if (out != null) {
                return Optional.of(SkosConcepts.fromSolr(out));
            }
        }
        return Optional.absent();
    }
    
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
}
