package uk.ac.ox.it.skossuggester.resources;

import io.dropwizard.jersey.params.IntParam;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryResponseParser;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class Search {

    private final HttpSolrServer solr;
    
    public Search(HttpSolrServer solr) {
        this.solr = solr;
    }
    
    @GET
    public SkosConcepts search(@QueryParam("q") String query,
                               @QueryParam("count") @DefaultValue("20") IntParam count,
                               @QueryParam("page") @DefaultValue("0") IntParam page) {
        SolrQuery q = new SolrQuery();
        q.setQuery(query);
        q.setStart(count.get()*page.get());
        q.setRows(count.get());
        
        QueryRequest req = new QueryRequest(q);
        req.setResponseParser(new BinaryResponseParser());
        QueryResponse rsp;
        try {
            rsp = req.process(solr);
            SolrDocumentList out = rsp.getResults();
            if (out != null) {
                return SkosConcepts.fromSolr(out);
            } else {
                throw new WebApplicationException(404);
            }
        } catch (SolrServerException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
