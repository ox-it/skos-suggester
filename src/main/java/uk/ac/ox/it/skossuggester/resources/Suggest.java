package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.jerseyutils.PositiveIntParam;
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
    public HalRepresentation suggest(@QueryParam("q") String query,
                                @QueryParam("page") @DefaultValue("1") PositiveIntParam page,
                                @QueryParam("count") @DefaultValue("20") PositiveIntParam count) {
        int firstResult = PaginationUtils.getFirstResult(page.get(), count.get());
        Optional<SkosConcepts> concepts = dao.suggest(query, firstResult, count.get());
        HalRepresentation hal = new HalRepresentation();
        hal.setSelfLink(new HalLink(UriBuilder.fromResource(Suggest.class).queryParam("q", query).build().toString()));
        hal.setEmbedded(concepts.or(new SkosConcepts()));
        return hal;
    }
}