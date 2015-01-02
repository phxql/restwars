package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.PlanetMapper;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.restapi.dto.planet.PlanetResponse;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/planet")
@Api(value = "/v1/planet", description = "Planet management", authorizations = {
        @Authorization("basicAuth")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanetResource {
    private final PlanetService planetService;
    private final BuildingSubResource buildingSubResource;
    private final ConstructionSiteSubResource constructionSiteSubResource;
    private final ResearchSubResource researchSubResource;
    private final ShipInConstructionSubResource shipInConstructionSubResource;
    private final ShipSubResource shipSubResource;
    private final FlightSubResource flightSubResource;

    @Inject
    public PlanetResource(PlanetService planetService, BuildingSubResource buildingSubResource, ConstructionSiteSubResource constructionSiteSubResource, ResearchSubResource researchSubResource, ShipInConstructionSubResource shipInConstructionSubResource, ShipSubResource shipSubResource, FlightSubResource flightSubResource) {
        this.flightSubResource = Preconditions.checkNotNull(flightSubResource, "flightSubResource");
        this.shipSubResource = Preconditions.checkNotNull(shipSubResource, "shipSubResource");
        this.shipInConstructionSubResource = Preconditions.checkNotNull(shipInConstructionSubResource, "shipInConstructionSubResource");
        this.researchSubResource = Preconditions.checkNotNull(researchSubResource, "researchSubResource");
        this.constructionSiteSubResource = Preconditions.checkNotNull(constructionSiteSubResource, "constructionSiteSubResource");
        this.buildingSubResource = Preconditions.checkNotNull(buildingSubResource, "buildingSubResource");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
    }

    @GET
    @ApiOperation("Lists all planets for the current player")
    public List<PlanetResponse> index(@Auth @ApiParam(access = "internal") Player player) {
        Preconditions.checkNotNull(player, "player");
        List<Planet> planets = planetService.findWithOwner(player);

        return Helper.mapToList(planets, PlanetMapper::fromPlanet);
    }

    @GET
    @Path("/{location}")
    @ApiOperation("Returns the planet at the given location")
    public PlanetResponse getPlanet(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam(value = "Planet location", required = true) LocationParam location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        return PlanetMapper.fromPlanet(planet);
    }

    @Path("/{location}/building")
    @ApiOperation("Buildings")
    public BuildingSubResource getBuildings() {
        return buildingSubResource;
    }

    @Path("/{location}/construction-site")
    @ApiOperation("Construction sites")
    public ConstructionSiteSubResource getConstructionSites() {
        return constructionSiteSubResource;
    }

    @Path("/{location}/research")
    @ApiOperation("Researches")
    public ResearchSubResource getResearches() {
        return researchSubResource;
    }

    @Path("/{location}/ship-in-construction")
    @ApiOperation("Ships in construction")
    public ShipInConstructionSubResource getShipsInConstruction() {
        return shipInConstructionSubResource;
    }

    @Path("/{location}/ship")
    @ApiOperation("Ships")
    public ShipSubResource getShips() {
        return shipSubResource;
    }

    @Path("/{location}/flight")
    @ApiOperation("Incoming and outgoing flights")
    public FlightSubResource getFlights() {
        return flightSubResource;
    }
}
