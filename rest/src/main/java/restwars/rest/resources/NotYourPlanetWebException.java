package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotYourPlanetWebException extends WebApplicationException {
    public NotYourPlanetWebException() {
        super(Response.Status.FORBIDDEN);
    }
}
