package restwars.restapi;

import restwars.restapi.dto.ship.CreateFlightRequest;
import restwars.restapi.dto.ship.FlightResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet/{location}/flight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FlightResource {
    @GET
    @Path("/own")
    public List<FlightResponse> getOwnFlights(@PathParam("location") String location);

    @POST
    @Path("/to/{destination}")
    public FlightResponse createFlight(@PathParam("location") String start, @PathParam("destination") String destination, CreateFlightRequest body);
}
