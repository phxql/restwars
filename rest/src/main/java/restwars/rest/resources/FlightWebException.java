package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class FlightWebException extends WebApplicationException {
    public FlightWebException() {
        // TODO: REST beauty - Include reason
        super(Response.Status.PRECONDITION_FAILED);
    }
}
