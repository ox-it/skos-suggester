package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;

/**
 *
 * @author martinfilliau
 */
public class GetTest {
    
    private static final SkosConceptsDao dao = mock(SkosConceptsDao.class);
    
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new Get(dao))
            .build();

    private SkosConcept concept;
    
    @Before
    public void setup() {
        concept = new SkosConcept();
        concept.setPrefLabel("PrefLabel");
        concept.setUri("http://uri");
        when(dao.get(eq("http://uri"))).thenReturn(Optional.of(concept));
        Optional<SkosConcept> none = Optional.absent();
        when(dao.get(eq("lalala"))).thenReturn(none);
    }

    @Test
    public void testGetConcept() {
        SkosConcept result = resources.client().resource("/get?uri=http://uri").get(SkosConcept.class);
        assertEquals(result, concept);
        verify(dao).get("http://uri");
    }
    
    @Test
    public void testNonExistingConcept() {
        ClientResponse response = resources.client().resource("/get?uri=lalala").get(ClientResponse.class);
        assertEquals(response.getStatus(), 404);
        verify(dao).get("lalala");
    }
}
