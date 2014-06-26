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

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class Search {

    private final SkosConceptsDao dao;
    
    public Search(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    public HalRepresentation search(@QueryParam("q") Optional<String> query,
                               @QueryParam("page") @DefaultValue("1") PositiveIntParam page,
                               @QueryParam("count") @DefaultValue("20") PositiveIntParam count) {
        // TODO better handling of the query
        // there seem to be some problems upstream in jersey in bean validation
        // so this should be revisited at a later date
        if(query.isPresent()) {
            int firstResult = (page.get()-1)*count.get();
            Optional<SkosConcepts> concepts = dao.search(query.get(), firstResult, count.get());
            HalRepresentation hal = new HalRepresentation();
            hal.setSelfLink(new HalLink(UriBuilder.fromResource(Search.class).queryParam("q", query.get()).build().toString()));
            hal.addEmbedded(concepts.or(new SkosConcepts()));
            return hal;
        } else {
            HalRepresentation hal = new HalRepresentation();
            hal.setSelfLink(new HalLink(UriBuilder.fromResource(Search.class).queryParam("q", query.get()).build().toString()));
            hal.addEmbedded(new SkosConcepts());
            return hal;
        }
    }
}
