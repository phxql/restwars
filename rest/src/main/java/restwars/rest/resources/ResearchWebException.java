package restwars.rest.resources;

import restwars.service.technology.ResearchException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResearchWebException extends WebApplicationException {
    public static class Body {
        private final ResearchException.Reason reason;

        public Body(ResearchException.Reason reason) {
            this.reason = reason;
        }

        public ResearchException.Reason getReason() {
            return reason;
        }
    }

    public ResearchWebException(ResearchException.Reason reason) {
        super(Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason)).build());
    }
}
