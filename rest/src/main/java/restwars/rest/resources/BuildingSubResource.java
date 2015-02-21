package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.model.building.Buildings;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.rest.mapper.BuildingMapper;
import restwars.restapi.dto.building.BuildingsResponse;
import restwars.service.building.BuildingService;
import restwars.service.planet.PlanetService;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Subresource for buildings on a planet.
 */
@Api(value = "/{location}/building", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BuildingSubResource {
    private final PlanetService planetService;
    private final BuildingService buildingService;

    @Inject
    public BuildingSubResource(BuildingService buildingService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    /**
     * Lists all buildings on the planet with the given location.
     *
     * @param player   Player.
     * @param location Planet location.
     * @return All buildings on the planet.
     */
    @GET
    @ApiOperation(value = "Get all buildings on a planet")
    public BuildingsResponse getBuildings(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") String location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = ResourceHelper.findPlanetWithLocationAndOwner(planetService, location, player);
        Buildings buildings = buildingService.findBuildingsOnPlanet(planet);

        return new BuildingsResponse(Functional.mapToList(buildings, BuildingMapper::fromBuilding));
    }
}
