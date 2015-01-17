package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Is thrown if the access to a resource is denied.
 */
public class AccessDeniedWebException extends WebApplicationException {
    public AccessDeniedWebException() {
        super(Response.Status.FORBIDDEN);
    }
}
