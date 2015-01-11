package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.ConstructionSiteMapper;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.restapi.dto.building.ConstructionSiteResponse;
import restwars.restapi.dto.building.CreateBuildingRequest;
import restwars.service.building.BuildingException;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.building.ConstructionSite;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(value = "/{location}/construction-site", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConstructionSiteSubResource {
    private final PlanetService planetService;
    private final BuildingService buildingService;

    @Inject
    public ConstructionSiteSubResource(PlanetService planetService, BuildingService buildingService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    @GET
    @ApiOperation(value = "Get all construction sites on a planet")
    public List<ConstructionSiteResponse> getConstructionSites(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        List<ConstructionSite> constructionSites = buildingService.findConstructionSitesOnPlanet(planet);

        return Helper.mapToList(constructionSites, ConstructionSiteMapper::fromConstructionSite);
    }

    @POST
    @ApiOperation("Creates a new construction site")
    public ConstructionSiteResponse build(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location,
            @Valid CreateBuildingRequest data
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(data, "data");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        BuildingType type = Helper.parseBuildingType(data.getType());

        try {
            ConstructionSite constructionSite = buildingService.constructOrUpgradeBuilding(planet, type);

            return ConstructionSiteMapper.fromConstructionSite(constructionSite);
        } catch (BuildingException e) {
            throw new BuildingWebException(e.getReason());
        }
    }
}
