package uk.ac.ox.it.skossuggester.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import io.dropwizard.jersey.caching.CacheControl;
import java.util.concurrent.TimeUnit;
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
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.HOURS)
    @Timed
    public SkosConcept get(@QueryParam("uri") String uri) {
        Preconditions.checkArgument(uri != null, "'uri' parameter is mandatory");
        Preconditions.checkArgument(!"".equals(uri), "'uri' parameter cannot be empty");

        Optional<SkosConcept> concept = this.dao.get(uri);
        if (concept.isPresent()) {
            return concept.get();
        } else {
            throw new WebApplicationException(404);
        }
    }
}