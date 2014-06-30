package uk.ac.ox.it.skossuggester.resources;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author martinfilliau
 */
public class PaginationUtilsTest {
 
    @Test
    public void testZero() {
        int firstResult = PaginationUtils.getFirstResult(0, 10);
        assertEquals(firstResult, 0);
    }
    
    public void testPage() {
        int firstResult = PaginationUtils.getFirstResult(2, 10);
        assertEquals(firstResult, 10);
    }
    
}
