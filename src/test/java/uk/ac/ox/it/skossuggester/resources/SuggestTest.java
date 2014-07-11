package uk.ac.ox.it.skossuggester.resources;

import java.util.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;
import static org.junit.Assert.*;
import uk.ac.ox.it.skossuggester.representations.hal.HalRepresentation;

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
        HalRepresentation result = resources.client().resource("/suggest?q=sec").get(HalRepresentation.class);
        SkosConcepts skos = result.getEmbedded();
        assertEquals(skos, concepts);
        verify(dao).suggest("sec", 0, 20);
    }
}
