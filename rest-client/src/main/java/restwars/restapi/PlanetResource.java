package restwars.restapi;

import restwars.restapi.dto.planet.PlanetResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PlanetResource {
    @GET
    List<PlanetResponse> allPlanets();

    @GET
    @Path("/{location}")
    public PlanetResponse getPlanet(@PathParam("location") String location);
}
