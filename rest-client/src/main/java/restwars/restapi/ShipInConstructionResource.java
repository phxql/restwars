package restwars.restapi;

import restwars.restapi.dto.ship.BuildShipRequest;
import restwars.restapi.dto.ship.ShipInConstructionResponse;
import restwars.restapi.dto.ship.ShipsInConstructionResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet/{location}/ship-in-construction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ShipInConstructionResource {
    @GET
    ShipsInConstructionResponse getShipsInConstruction(@PathParam("location") String location);

    @POST
    ShipInConstructionResponse buildShip(@PathParam("location") String location, BuildShipRequest data);
}
