package restwars.restapi;

import restwars.restapi.dto.building.ConstructionSiteResponse;
import restwars.restapi.dto.building.ConstructionSitesResponse;
import restwars.restapi.dto.building.CreateBuildingRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet/{location}/construction-site")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ConstructionSiteResource {
    @GET
    ConstructionSitesResponse getConstructionSites(@PathParam("location") String location);

    @POST
    ConstructionSiteResponse build(@PathParam("location") String location, CreateBuildingRequest data);
}
