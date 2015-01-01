package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.rest.api.ship.BuildShipRequest;
import restwars.rest.api.ship.ShipInConstructionResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.ship.BuildShipException;
import restwars.service.ship.ShipInConstruction;
import restwars.service.ship.ShipService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import java.util.List;

@Api(value = "/{location}/ship-in-construction", hidden = true)
public class ShipInConstructionSubResource {
    private final ShipService shipService;
    private final PlanetService planetService;

    @Inject
    public ShipInConstructionSubResource(ShipService shipService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
    }

    @GET
    @ApiOperation("Lists all ships in construction on the planet")
    public List<ShipInConstructionResponse> getShipsInConstruction(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        List<ShipInConstruction> shipsInConstruction = shipService.findShipsInConstructionOnPlanet(planet);
        return Helper.mapToList(shipsInConstruction, ShipInConstructionResponse::fromShipInConstruction);
    }

    @POST
    @ApiOperation("Builds a ship")
    public ShipInConstructionResponse buildShip(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location,
            @Valid BuildShipRequest data
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(data, "data");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        try {
            ShipInConstruction shipInConstruction = shipService.buildShip(player, planet, data.getParsedType());
            return ShipInConstructionResponse.fromShipInConstruction(shipInConstruction);
        } catch (BuildShipException e) {
            throw new BuildShipWebException(e.getReason());
        }
    }
}
