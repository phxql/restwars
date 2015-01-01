package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BuildingWebException extends WebApplicationException {
    public BuildingWebException() {
        // TODO: Rest beauty - include reason
        super(Response.Status.PRECONDITION_FAILED);
    }
}
