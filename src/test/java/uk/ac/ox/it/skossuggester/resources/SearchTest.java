package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;
import static org.junit.Assert.*;

/**
 *
 * @author martinfilliau
 */
public class SearchTest {
    
    private static final SkosConceptsDao dao = mock(SkosConceptsDao.class);
    
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new Search(dao))
            .build();

    private SkosConcepts concepts;
    
    @Before
    public void setup() {
        concepts = new SkosConcepts();
        SkosConcept concept = new SkosConcept();
        concept.setPrefLabel("PrefLabel");
        concept.setUri("http://uri");
        concepts.addConcept(concept);
        when(dao.search(eq("encryption"), any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.of(concepts));
        Optional<SkosConcepts> none = Optional.absent();
        when(dao.search(eq("lalala"), any(Integer.class), any(Integer.class))).thenReturn(none);
    }

    @Test
    public void testSearchConcepts() {
        SkosConcepts result = resources.client().resource("/search?q=encryption").get(SkosConcepts.class);
        assertEquals(result, concepts);
        verify(dao).search("encryption", 0, 20);
    }
    
    @Test
    public void testNoResults() {
        SkosConcepts result = resources.client().resource("/search?q=lalala").get(SkosConcepts.class);
        assertEquals(result, new SkosConcepts());   // empty result set
        verify(dao).search("lalala", 0, 20);
    }
    
    @Test
    public void testSearchPagination() {
        resources.client().resource("/search?q=encryption&page=2&count=2").get(SkosConcepts.class);
        verify(dao).search("encryption", 2, 2);
        resources.client().resource("/search?q=encryption&page=1&count=10").get(SkosConcepts.class);
        verify(dao).search("encryption", 0, 10);
        resources.client().resource("/search?q=encryption&page=2&count=10").get(SkosConcepts.class);
        verify(dao).search("encryption", 10, 10);
    }
    
    @Test
    public void testPaginationValidation() {
        ClientResponse response = resources.client()
                .resource("/search")
                .queryParam("q", "encryption")
                .queryParam("page", "0")
                .queryParam("count", "10")
                .get(ClientResponse.class);
        assertEquals(response.getStatus(), 400);
    }
}
