package restwars.restapi;

import restwars.restapi.dto.ship.ShipResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet/{location}/ship")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ShipResource {
    @GET
    List<ShipResponse> getShips(@PathParam("location") String location);
}
