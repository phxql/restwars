package restwars.restapi;

import restwars.restapi.dto.building.BuildingResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet/{location}/building")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BuildingResource {
    @GET
    List<BuildingResponse> getBuildings(@PathParam("location") String location);
}
