package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ResearchWebException extends WebApplicationException {
    public ResearchWebException() {
        // TODO: Rest beauty - include reason
        super(Response.Status.PRECONDITION_FAILED);
    }
}
