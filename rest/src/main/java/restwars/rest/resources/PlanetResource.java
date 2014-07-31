package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.planet.PlanetResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanetResource {
    private final PlanetService planetService;
    private final BuildingSubResource buildingSubResource;
    private final ConstructionSiteSubResource constructionSiteSubResource;
    private final ResearchSubResource researchSubResource;
    private final ShipInConstructionSubResource shipInConstructionSubResource;
    private final ShipSubResource shipSubResource;

    @Inject
    public PlanetResource(PlanetService planetService, BuildingSubResource buildingSubResource, ConstructionSiteSubResource constructionSiteSubResource, ResearchSubResource researchSubResource, ShipInConstructionSubResource shipInConstructionSubResource, ShipSubResource shipSubResource) {
        this.shipSubResource = Preconditions.checkNotNull(shipSubResource, "shipSubResource");
        this.shipInConstructionSubResource = Preconditions.checkNotNull(shipInConstructionSubResource, "shipInConstructionSubResource");
        this.researchSubResource = Preconditions.checkNotNull(researchSubResource, "researchSubResource");
        this.constructionSiteSubResource = Preconditions.checkNotNull(constructionSiteSubResource, "constructionSiteSubResource");
        this.buildingSubResource = Preconditions.checkNotNull(buildingSubResource, "buildingSubResource");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
    }

    @GET
    public List<PlanetResponse> index(@Auth Player player) {
        Preconditions.checkNotNull(player, "player");
        List<Planet> planets = planetService.findWithOwner(player);

        return Helper.mapToList(planets, PlanetResponse::fromPlanet);
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
        return buildingSubResource;
    }

    @Path("/{location}/construction-site")
    public ConstructionSiteSubResource getConstructionSites() {
        return constructionSiteSubResource;
    }

    @Path("/{location}/research")
    public ResearchSubResource getResearches() {
        return researchSubResource;
    }

    @Path("/{location}/ship-in-construction")
    public ShipInConstructionSubResource getShipsInConstruction() {
        return shipInConstructionSubResource;
    }

    @Path("/{location}/ship")
    public ShipSubResource getShips() {
        return shipSubResource;
    }

}
