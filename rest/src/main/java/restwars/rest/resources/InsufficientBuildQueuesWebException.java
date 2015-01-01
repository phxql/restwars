package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InsufficientBuildQueuesWebException extends WebApplicationException {
    public InsufficientBuildQueuesWebException() {
        // TODO: REST beauty - Include reason
        super(Response.Status.PRECONDITION_FAILED);
    }
}
