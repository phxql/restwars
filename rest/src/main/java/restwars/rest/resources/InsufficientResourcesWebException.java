package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InsufficientResourcesWebException extends WebApplicationException {
    public InsufficientResourcesWebException() {
        // TODO: REST beauty - Include reason
        super(Response.Status.PRECONDITION_FAILED);
    }
}
