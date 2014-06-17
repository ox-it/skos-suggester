package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;

@Path("/get")
@Produces(MediaType.APPLICATION_JSON)
public class Get {

    private final SkosConceptsDao dao;
    
    public Get(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public SkosConcept get(@QueryParam("uri") String uri) {
        Optional<SkosConcept> concept = this.dao.get(uri);
        if (concept.isPresent()) {
            return concept.get();
        } else {
            throw new WebApplicationException(404);
        }
    }
}