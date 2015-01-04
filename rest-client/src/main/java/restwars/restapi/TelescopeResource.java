package restwars.restapi;

import restwars.restapi.dto.planet.PlanetScanResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet/{location}/telescope")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TelescopeResource {
    @GET
    @Path("/scan")
    List<PlanetScanResponse> scan(@PathParam("location") String location);
}
