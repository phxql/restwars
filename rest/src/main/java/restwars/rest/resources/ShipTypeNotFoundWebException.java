package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ShipTypeNotFoundWebException extends WebApplicationException {
    public static enum Reason {
        INVALID_SHIP_TYPE
    }

    /**
     * Response body.
     */
    public static class Body {
        private final Reason reason;

        public Body(Reason reason) {
            this.reason = reason;
        }

        public Reason getReason() {
            return reason;
        }
    }

    public ShipTypeNotFoundWebException() {
        super(Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(new Body(Reason.INVALID_SHIP_TYPE)).build());
    }
}
