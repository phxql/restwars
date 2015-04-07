package restwars.rest.resources;

import restwars.service.telescope.ScanException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ScanWebException extends WebApplicationException {
    public static class Body {
        private final String reason;

        public Body(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }

    public ScanWebException(Throwable cause, ScanException.Reason reason) {
        super(cause, Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason.name())).build());
    }
}
