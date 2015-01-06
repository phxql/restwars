package restwars.restapi;

import restwars.restapi.dto.event.EventResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EventResource {
    @GET
    List<EventResponse> getEvents(@QueryParam("since") long round);
}
