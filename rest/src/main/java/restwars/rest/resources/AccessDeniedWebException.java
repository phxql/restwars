package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AccessDeniedWebException extends WebApplicationException {
    public AccessDeniedWebException() {
        super(Response.Status.FORBIDDEN);
    }
}
