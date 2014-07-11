package uk.ac.ox.it.skossuggester.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.List;
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

@Path("/get")
@Produces(MediaType.APPLICATION_JSON)
public class Get {

    private final SkosConceptsDao dao;
    
    public Get(SkosConceptsDao dao) {
        this.dao = dao;
    }
    
    @GET
    @Timed
    public HalRepresentation get(@QueryParam("uri") List<String> uris) {
        Preconditions.checkArgument(uris != null, "'uri' parameter is mandatory");
        //Preconditions.checkArgument(!"".equals(uri), "'uri' parameter cannot be empty");

        Optional<SkosConcepts> concepts = this.dao.get(uris);

        HalRepresentation hal = new HalRepresentation();
        UriBuilder uri = UriBuilder.fromResource(Search.class);
        for (String u : uris) {
            uri.queryParam("uri", u);
        }
        hal.setSelfLink(new HalLink(uri.build().toString()));
        hal.setEmbedded(concepts.or(new SkosConcepts()));
        return hal;
    }
}