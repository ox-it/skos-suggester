package uk.ac.ox.it.skossuggester.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Test;
import uk.ac.ox.it.skossuggester.SkosSuggesterApplication;
import uk.ac.ox.it.skossuggester.configuration.AppConfiguration;

public class QueryParametersTest {
    
    // Testing the response after having properly started the application as we
    // have a custom provider to handle IllegalArgumentException

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE = 
            new DropwizardAppRule<>(SkosSuggesterApplication.class, null);

    @Test
    public void testPaginationValidation() {
        
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/search", RULE.getLocalPort()))
                .queryParam("q", "encryption")
                .queryParam("page", "0")
                .queryParam("count", "10")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void testMissingQuery() {
        
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/suggest", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(400));
    }
}
