package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotEnoughShipsWebException extends WebApplicationException {
    public NotEnoughShipsWebException() {
        super(Response.Status.PRECONDITION_FAILED);
    }
}