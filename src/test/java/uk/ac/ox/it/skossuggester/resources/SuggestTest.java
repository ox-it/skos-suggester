package uk.ac.ox.it.skossuggester.resources;

import com.google.common.base.Optional;
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
public class SuggestTest {
    
    private static final SkosConceptsDao dao = mock(SkosConceptsDao.class);
    
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new Suggest(dao))
            .build();

    private SkosConcepts concepts;
    
    @Before
    public void setup() {
        concepts = new SkosConcepts();
        SkosConcept concept = new SkosConcept();
        concept.setPrefLabel("Computer Security");
        concept.setUri("http://uri");
        concepts.addConcept(concept);
        when(dao.suggest(eq("sec"), any(Integer.class), any(Integer.class))).thenReturn(Optional.of(concepts));
    }

    @Test
    public void testSuggestConcepts() {
        SkosConcepts result = resources.client().resource("/suggest?q=sec").get(SkosConcepts.class);
        assertEquals(result, concepts);
        verify(dao).suggest("sec", 1, 20);
    }
}
