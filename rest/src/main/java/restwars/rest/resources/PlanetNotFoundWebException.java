package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class PlanetNotFoundWebException extends WebApplicationException {
    public PlanetNotFoundWebException() {
        super(Response.Status.NOT_FOUND);
    }
}
