package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;

@Path("/suggest")
@Produces(MediaType.APPLICATION_JSON)
public class Suggest {
    private final SkosConceptsDao dao;
    
    public Suggest(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public SkosConcepts suggest(@QueryParam("q") String query) {
        Optional<SkosConcepts> concepts = dao.suggest(query);
        return concepts.or(new SkosConcepts());
    }

}