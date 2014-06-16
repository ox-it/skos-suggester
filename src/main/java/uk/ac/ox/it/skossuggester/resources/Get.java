package uk.ac.ox.it.skossuggester.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.solr.common.SolrDocument;

@Path("/get")
@Produces(MediaType.APPLICATION_JSON)
public class Get {

    private final HttpSolrServer solr;
    
    public Get(HttpSolrServer solr) {
        this.solr = solr;
    }
    
    @GET
    public String get(@QueryParam("uri") String uri) {
        SolrQuery q = new SolrQuery();
        q.setRequestHandler("/get");
        q.set("id", uri);
        QueryRequest req = new QueryRequest(q);
        req.setResponseParser(new BinaryResponseParser());
        QueryResponse rsp;
        try {
            rsp = req.process(solr);
            SolrDocument out = (SolrDocument)rsp.getResponse().get("doc");
            if (out != null) {
                return out.toString();
                //return Tag.fromSolrDocument(out);
            } else {
                throw new WebApplicationException(404);
            }
        } catch (SolrServerException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}