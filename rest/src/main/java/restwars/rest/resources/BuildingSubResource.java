package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.building.BuildingResponse;
import restwars.rest.api.building.ConstructionSiteResponse;
import restwars.rest.api.building.CreateBuildingRequest;
import restwars.rest.resources.param.LocationParam;
import restwars.service.building.Building;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.building.ConstructionSite;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.stream.Collectors;

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

        return buildings.stream().map(BuildingResponse::fromBuilding).collect(Collectors.toList());
    }

    @POST
    public ConstructionSiteResponse build(@Auth Player player, @PathParam("location") LocationParam location, @Valid CreateBuildingRequest data) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(data, "data");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        BuildingType type = Helper.parseBuildingType(data.getType());

        ConstructionSite constructionSite = buildingService.constructOrUpgradeBuilding(planet, type);

        return ConstructionSiteResponse.fromConstructionSite(constructionSite);
    }
}
