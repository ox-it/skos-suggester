package uk.ac.ox.it.skossuggester.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import io.dropwizard.jersey.params.IntParam;
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

@Path("/suggest")
@Produces(MediaType.APPLICATION_JSON)
public class Suggest {
    private final SkosConceptsDao dao;
    
    public Suggest(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    @Timed
    public HalRepresentation suggest(@QueryParam("q") String query,
                                @QueryParam("page") @DefaultValue("1") IntParam page,
                                @QueryParam("count") @DefaultValue("20") IntParam count) {
        Preconditions.checkArgument(query != null, "'q' parameter is mandatory");
        Preconditions.checkArgument(!"".equals(query), "'q' parameter cannot be empty");
        Preconditions.checkArgument(page.get() > 0, "'page' must be greater than 0");
        Preconditions.checkArgument(count.get() > 1, "'count' must be greater than 1");

        int firstResult = PaginationUtils.getFirstResult(page.get(), count.get());
        Optional<SkosConcepts> concepts = dao.suggest(query, firstResult, count.get());
        HalRepresentation hal = new HalRepresentation();
        hal.setSelfLink(new HalLink(UriBuilder.fromResource(Suggest.class).queryParam("q", query).build().toString()));
        hal.setEmbedded(concepts.or(new SkosConcepts()));
        return hal;
    }
}