package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.planet.PlanetResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/planet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanetResource {
    private final PlanetService planetService;
    private final BuildingSubResource buildingSubResource;
    private final ConstructionSiteSubResource constructionSiteSubResource;

    public PlanetResource(PlanetService planetService, BuildingSubResource buildingSubResource, ConstructionSiteSubResource constructionSiteSubResource) {
        this.constructionSiteSubResource = Preconditions.checkNotNull(constructionSiteSubResource, "constructionSiteSubResource");
        this.buildingSubResource = Preconditions.checkNotNull(buildingSubResource, "buildingSubResource");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
    }

    @GET
    public List<PlanetResponse> index(@Auth Player player) {
        Preconditions.checkNotNull(player, "player");
        List<Planet> planets = planetService.findWithOwner(player);

        return planets.stream().map(PlanetResponse::fromPlanet).collect(Collectors.toList());
    }

    @GET
    @Path("/{location}")
    public PlanetResponse getPlanet(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        return PlanetResponse.fromPlanet(planet);
    }

    @Path("/{location}/building")
    public BuildingSubResource getBuildings() {
        return this.buildingSubResource;
    }

    @Path("/{location}/construction-site")
    public ConstructionSiteSubResource getConstructionSites() {
        return this.constructionSiteSubResource;
    }
}
