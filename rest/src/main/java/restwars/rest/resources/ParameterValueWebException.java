package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Is thrown if a parameter has an invalid value.
 */
public class ParameterValueWebException extends WebApplicationException {
    public static class Body {
        private final String reason;

        public Body(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }

    /**
     * Constructor.
     *
     * @param reason Reason.
     */
    public ParameterValueWebException(String reason) {
        super(Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason)).build());
    }
}
