package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.BuildingMapper;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.restapi.dto.building.BuildingResponse;
import restwars.service.building.BuildingService;
import restwars.service.building.Buildings;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public List<BuildingResponse> getBuildings(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        Buildings buildings = buildingService.findBuildingsOnPlanet(planet);

        return Helper.mapToList(buildings, BuildingMapper::fromBuilding);
    }
}
