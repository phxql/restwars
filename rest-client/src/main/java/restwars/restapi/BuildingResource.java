package restwars.restapi;

import restwars.restapi.dto.building.BuildingsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet/{location}/building")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BuildingResource {
    @GET
    BuildingsResponse getBuildings(@PathParam("location") String location);
}
