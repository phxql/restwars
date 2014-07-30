package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.building.BuildingResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.service.building.Building;
import restwars.service.building.BuildingService;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import java.util.List;

public class BuildingSubResource {
    private final PlanetService planetService;
    private final BuildingService buildingService;

    public BuildingSubResource(BuildingService buildingService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    @GET
    public List<BuildingResponse> getBuildings(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        List<Building> buildings = buildingService.findBuildingsOnPlanet(planet);

        return Helper.mapToList(buildings, BuildingResponse::fromBuilding);
    }
}
