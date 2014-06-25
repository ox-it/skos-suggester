package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.jerseyutils.PositiveIntParam;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;

@Path("/suggest")
@Produces(MediaType.APPLICATION_JSON)
public class Suggest {
    private final SkosConceptsDao dao;
    
    public Suggest(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public SkosConcepts suggest(@QueryParam("q") String query,
                                @QueryParam("page") @DefaultValue("1") PositiveIntParam page,
                                @QueryParam("count") @DefaultValue("20") PositiveIntParam count) {
        Optional<SkosConcepts> concepts = dao.suggest(query, page.get(), count.get());
        return concepts.or(new SkosConcepts());
    }

}