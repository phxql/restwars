package restwars.restapi;

import restwars.restapi.dto.ship.CreateFlightRequest;
import restwars.restapi.dto.ship.FlightResponse;
import restwars.restapi.dto.ship.FlightsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FlightResource {
    @GET
    @Path("flight/own")
    FlightsResponse getOwnFlights();

    @GET
    @Path("planet/{location}/flight/own")
    FlightsResponse getOwnFlightsForPlanet(@PathParam("location") String location);

    @POST
    @Path("planet/{location}/flight/to/{destination}")
    FlightResponse createFlight(@PathParam("location") String start, @PathParam("destination") String destination, CreateFlightRequest body);
}
