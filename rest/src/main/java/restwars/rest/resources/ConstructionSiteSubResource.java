package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.model.building.BuildingType;
import restwars.model.building.ConstructionSite;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.rest.mapper.ConstructionSiteMapper;
import restwars.restapi.dto.building.ConstructionSiteResponse;
import restwars.restapi.dto.building.ConstructionSitesResponse;
import restwars.restapi.dto.building.CreateBuildingRequest;
import restwars.service.building.BuildingException;
import restwars.service.building.BuildingService;
import restwars.service.planet.PlanetService;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Subresource for construction sites on a planet.
 */
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

    /**
     * Lists all construction sites on the planet with the given location.
     *
     * @param player   Player.
     * @param location Planet location.
     * @return All construction sites on the planet.
     */
    @GET
    @ApiOperation(value = "Get all construction sites on a planet")
    public ConstructionSitesResponse getConstructionSites(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") String location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = ResourceHelper.findPlanetWithLocationAndOwner(planetService, location, player);
        List<ConstructionSite> constructionSites = buildingService.findConstructionSitesOnPlanet(planet);

        return new ConstructionSitesResponse(Functional.mapToList(constructionSites, ConstructionSiteMapper::fromConstructionSite));
    }

    /**
     * Creates a new construction site on the planet with the given location.
     *
     * @param player   Player.
     * @param location Planet location.
     * @param data     Information about the new construction site.
     * @return The constructed construction site.
     */
    @POST
    @ApiOperation("Creates a new construction site")
    public ConstructionSiteResponse build(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") String location,
            @Valid CreateBuildingRequest data
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(data, "data");

        Planet planet = ResourceHelper.findPlanetWithLocationAndOwner(planetService, location, player);
        BuildingType type = ResourceHelper.parseBuildingType(data.getType());

        try {
            ConstructionSite constructionSite = buildingService.constructOrUpgradeBuilding(planet, type);

            return ConstructionSiteMapper.fromConstructionSite(constructionSite);
        } catch (BuildingException e) {
            throw new BuildingWebException(e.getReason());
        }
    }
}
