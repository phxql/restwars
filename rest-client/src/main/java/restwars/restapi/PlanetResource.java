package restwars.restapi;

import restwars.restapi.dto.planet.PlanetWithResourcesResponse;
import restwars.restapi.dto.planet.PlanetsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PlanetResource {
    @GET
    PlanetsResponse allPlanets();

    @GET
    @Path("/{location}")
    PlanetWithResourcesResponse getPlanet(@PathParam("location") String location);
}
