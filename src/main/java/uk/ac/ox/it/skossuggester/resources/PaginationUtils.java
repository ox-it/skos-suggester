package uk.ac.ox.it.skossuggester.resources;

/**
 *
 * @author martinfilliau
 */
public class PaginationUtils {
    
    /**
     * Get the first result for a given page and count
     * @param page page number
     * @param count number of results
     * @return first result of the page
     */
    public static int getFirstResult(int page, int count) {
        if (page < 1) {
            page = 1;
        }
        return (page-1)*count;
    }
}
