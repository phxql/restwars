package restwars.rest.resources;

import restwars.service.player.CreatePlayerException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CreatePlayerWebException extends WebApplicationException {
    public static class Body {
        private final CreatePlayerException.Reason reason;

        public Body(CreatePlayerException.Reason reason) {
            this.reason = reason;
        }

        public CreatePlayerException.Reason getReason() {
            return reason;
        }
    }

    public CreatePlayerWebException(CreatePlayerException.Reason reason) {
        super(Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason)).build());
    }
}
