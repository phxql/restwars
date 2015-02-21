package restwars.restapi;

import restwars.restapi.dto.planet.PlanetScansResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet/{location}/telescope")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TelescopeResource {
    @GET
    @Path("/scan")
    PlanetScansResponse scan(@PathParam("location") String location);
}
