package restwars.rest.resources;

import restwars.service.flight.FlightException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Is thrown if a flight couldn't be started.
 */
public class FlightWebException extends WebApplicationException {
    /**
     * Response body.
     */
    public static class Body {
        private final FlightException.Reason reason;

        public Body(FlightException.Reason reason) {
            this.reason = reason;
        }

        public FlightException.Reason getReason() {
            return reason;
        }
    }

    public FlightWebException(Throwable cause, FlightException.Reason reason) {
        super(cause, Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason)).build());
    }
}
