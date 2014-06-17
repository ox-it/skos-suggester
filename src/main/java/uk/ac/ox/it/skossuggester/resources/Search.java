package uk.ac.ox.it.skossuggester.resources;

import io.dropwizard.jersey.params.IntParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class Search {

    private final SkosConceptsDao dao;
    
    public Search(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public SkosConcepts search(@QueryParam("q") String query,
                               @QueryParam("count") @DefaultValue("20") IntParam count,
                               @QueryParam("page") @DefaultValue("0") IntParam page) {
        SkosConcepts concepts = dao.search(query, page.get()*count.get(), count.get());
        if (concepts != null) {
            return concepts;
        } else {
            return new SkosConcepts();      // empty list for now?
        }
    }
}
