package restwars.restapi;

import restwars.restapi.dto.event.EventsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EventResource {
    @GET
    EventsResponse getEvents();

    @GET
    EventsResponse getEvents(@QueryParam("since") long round);

    @GET
    EventsResponse getEvents(@QueryParam("since") long round, @QueryParam("max") int max);
}
