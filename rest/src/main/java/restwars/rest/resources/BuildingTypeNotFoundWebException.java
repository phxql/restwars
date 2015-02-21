package restwars.rest.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BuildingTypeNotFoundWebException extends WebApplicationException {
    public static enum Reason {
        INVALID_BUILDING_TYPE
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

    public BuildingTypeNotFoundWebException() {
        super(Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(new Body(Reason.INVALID_BUILDING_TYPE)).build());
    }
}
