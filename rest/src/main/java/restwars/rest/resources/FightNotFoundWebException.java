package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Is thrown if a fight couldn't be found.
 */
public class FightNotFoundWebException extends WebApplicationException {
    public FightNotFoundWebException() {
        super(Response.Status.NOT_FOUND);
    }
}
