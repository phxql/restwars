package restwars.restapi;

import restwars.restapi.dto.building.ConstructionSiteResponse;
import restwars.restapi.dto.building.CreateBuildingRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet/{location}/construction-site")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ConstructionSiteResource {
    @GET
    List<ConstructionSiteResponse> getConstructionSites(@PathParam("location") String location);

    @POST
    ConstructionSiteResponse build(@PathParam("location") String location, CreateBuildingRequest data);
}
