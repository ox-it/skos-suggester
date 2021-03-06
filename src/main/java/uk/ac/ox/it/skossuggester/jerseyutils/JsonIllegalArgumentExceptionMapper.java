package uk.ac.ox.it.skossuggester.jerseyutils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
/**
 * Maps an IllegalArgumentException to a response HTTP 400 Bad Request
 */
public class JsonIllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    public class ErrorMessage {
        
        private final String message;
        
        public ErrorMessage(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return this.message;
        }
    }
    
    @Override
    public Response toResponse(IllegalArgumentException error) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(error.getMessage())).build();
    }
}
