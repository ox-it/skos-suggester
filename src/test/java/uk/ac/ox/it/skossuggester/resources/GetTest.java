package uk.ac.ox.it.skossuggester.resources;

import java.util.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import uk.ac.ox.it.skossuggester.dao.SkosConceptsDao;
import uk.ac.ox.it.skossuggester.representations.SkosConcept;
import uk.ac.ox.it.skossuggester.representations.SkosConcepts;
import uk.ac.ox.it.skossuggester.representations.hal.HalRepresentation;

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

    private final SkosConcepts concepts = new SkosConcepts();
    private final List<String> validUris = new ArrayList<>();
    private final List<String> noneUris = new ArrayList<>();
    
    @Before
    public void setup() {
        SkosConcept concept = new SkosConcept();
        concept.setPrefLabel("PrefLabel");
        concept.setUri("http://uri");
        concepts.addConcept(concept);
        validUris.add("http://uri");
        noneUris.add("lalala");
        when(dao.get(eq(validUris))).thenReturn(Optional.of(concepts));
        Optional<SkosConcepts> none = Optional.empty();
        when(dao.get(eq(noneUris))).thenReturn(none);
    }

    @Test
    public void testGetConcept() {
        HalRepresentation result = resources.client().resource("/get?uri=http://uri").get(HalRepresentation.class);
        assertEquals(result.getEmbedded(), concepts);
        verify(dao).get(validUris);
    }
    
    @Test
    public void testNonExistingConcept() {
        HalRepresentation response = resources.client().resource("/get?uri=lalala").get(HalRepresentation.class);
        assertEquals(response.getEmbedded().getConcepts().size(), 0);
        verify(dao).get(noneUris);
    }
}
