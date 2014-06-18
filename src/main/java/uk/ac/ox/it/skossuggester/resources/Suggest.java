package uk.ac.ox.it.skossuggester.resources;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;

@Path("/suggest")
@Produces(MediaType.APPLICATION_JSON)
public class Suggest {
    private final SkosConceptsDao dao;
    
    public Suggest(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public List<String> suggest(@QueryParam("q") String query) {
        return dao.suggest(query);
    }

}