package restwars.rest.resources;

import restwars.service.ship.BuildShipException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Is thrown if a ship couldn't be created.
 */
public class BuildShipWebException extends WebApplicationException {
    /**
     * Response body.
     */
    public static class Body {
        private final BuildShipException.Reason reason;

        public Body(BuildShipException.Reason reason) {
            this.reason = reason;
        }

        public BuildShipException.Reason getReason() {
            return reason;
        }
    }

    public BuildShipWebException(BuildShipException.Reason reason) {
        super(Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason)).build());
    }
}
