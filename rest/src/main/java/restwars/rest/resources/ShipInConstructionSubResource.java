package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.ship.ShipInConstruction;
import restwars.model.ship.ShipType;
import restwars.rest.mapper.ShipInConstructionMapper;
import restwars.rest.resources.param.LocationParam;
import restwars.restapi.dto.ship.BuildShipRequest;
import restwars.restapi.dto.ship.ShipInConstructionResponse;
import restwars.restapi.dto.ship.ShipsInConstructionResponse;
import restwars.service.planet.PlanetService;
import restwars.service.ship.BuildShipException;
import restwars.service.ship.ShipService;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(value = "/{location}/ship-in-construction", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
    public ShipsInConstructionResponse getShipsInConstruction(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = ResourceHelper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        List<ShipInConstruction> shipsInConstruction = shipService.findShipsInConstructionOnPlanet(planet);
        return new ShipsInConstructionResponse(
                Functional.mapToList(shipsInConstruction, ShipInConstructionMapper::fromShipInConstruction)
        );
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

        Planet planet = ResourceHelper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        try {
            ShipInConstruction shipInConstruction = shipService.buildShip(player, planet, ShipType.valueOf(data.getType()));
            return ShipInConstructionMapper.fromShipInConstruction(shipInConstruction);
        } catch (BuildShipException e) {
            throw new BuildShipWebException(e.getReason());
        }
    }
}
