package uk.ac.ox.it.skossuggester.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import java.util.ArrayList;
import java.util.List;
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
        when(dao.search(eq("encryption"), eq(0), eq(20))).thenReturn(concepts);
    }

    @Test
    public void testSearchConcepts() {
        SkosConcepts result = resources.client().resource("/search?q=encryption").get(SkosConcepts.class);
        assertEquals(result, concepts);
        verify(dao).search("encryption", 0, 20);
    }
}
