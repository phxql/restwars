package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InvalidFlightsWebException extends WebApplicationException {
    public InvalidFlightsWebException() {
        // TODO: REST beauty - Include reason
        super(Response.Status.PRECONDITION_FAILED);
    }
}
