package restwars.restapi;

import restwars.restapi.dto.ship.ShipsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet/{location}/ship")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ShipResource {
    @GET
    ShipsResponse getShips(@PathParam("location") String location);
}
