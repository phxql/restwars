package restwars.rest.resources;

import restwars.service.building.BuildingException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BuildingWebException extends WebApplicationException {
    public static class Body {
        private final BuildingException.Reason reason;

        public Body(BuildingException.Reason reason) {
            this.reason = reason;
        }

        public BuildingException.Reason getReason() {
            return reason;
        }
    }

    public BuildingWebException(BuildingException.Reason reason) {
        super(Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON).entity(new Body(reason)).build());
    }
}
