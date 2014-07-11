package uk.ac.ox.it.skossuggester.resources;

import com.codahale.metrics.annotation.Timed;
import java.util.Optional;
import com.google.common.base.Preconditions;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.jersey.params.IntParam;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;
import uk.ac.ox.it.skossuggester.representations.hal.HalLink;
import uk.ac.ox.it.skossuggester.representations.hal.HalRepresentation;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class Search {

    private final SkosConceptsDao dao;
    
    public Search(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.HOURS)
    @Timed
    public HalRepresentation search(@QueryParam("q") String query,
                               @QueryParam("page") @DefaultValue("1") IntParam page,
                               @QueryParam("count") @DefaultValue("20") IntParam count) {
        // TODO better handling of the query parameter, when Jersey will be in version 2+
        // we should be able to do everything using jersey-bean-validation and @Valid
        Preconditions.checkArgument(query != null, "'q' parameter is mandatory");
        Preconditions.checkArgument(!"".equals(query), "'q' parameter cannot be empty");
        Preconditions.checkArgument(page.get() > 0, "'page' must be greater than 0");
        Preconditions.checkArgument(count.get() > 1, "'count' must be greater than 1");

        HalRepresentation hal = new HalRepresentation();
        hal.setSelfLink(new HalLink(UriBuilder.fromResource(Search.class)
                .queryParam("q", query)
                .queryParam("page", page)
                .queryParam("count", count)
                .build().toString()));
        int firstResult = PaginationUtils.getFirstResult(page.get(), count.get());
        Optional<SkosConcepts> concepts = dao.search(query, firstResult, count.get());
        hal.setEmbedded(concepts.orElse(new SkosConcepts()));
        return hal;
    }
}
