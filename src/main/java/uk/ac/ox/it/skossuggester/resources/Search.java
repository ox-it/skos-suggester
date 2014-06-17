package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.jerseyutils.NotEmptyStringParam;
import uk.ac.ox.it.skossuggester.jerseyutils.PositiveIntParam;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class Search {

    private final SkosConceptsDao dao;
    
    public Search(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public SkosConcepts search(@QueryParam("q") NotEmptyStringParam query,
                               @QueryParam("page") @DefaultValue("1") PositiveIntParam page,
                               @QueryParam("count") @DefaultValue("20") PositiveIntParam count) {
        int firstResult = (page.get()-1)*count.get();
        Optional<SkosConcepts> concepts = dao.search(query.get(), firstResult, count.get());
        return concepts.or(new SkosConcepts());
    }
}
