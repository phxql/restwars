package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.ship.Ship;
import restwars.rest.mapper.ShipMapper;
import restwars.restapi.dto.ship.ShipsResponse;
import restwars.service.planet.PlanetService;
import restwars.service.ship.ShipService;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(value = "/{location}/ship", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShipSubResource {
    private final ShipService shipService;
    private final PlanetService planetService;

    @Inject
    public ShipSubResource(ShipService shipService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
    }

    @GET
    @ApiOperation("Lists all ships on the planet")
    public ShipsResponse getShips(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") String location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = ResourceHelper.findPlanetWithLocationAndOwner(planetService, location, player);
        List<Ship> ships = shipService.findShipsOnPlanet(planet).asList();

        return new ShipsResponse(Functional.mapToList(ships, ShipMapper::fromShip));
    }
}
