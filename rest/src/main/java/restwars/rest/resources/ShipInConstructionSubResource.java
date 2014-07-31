package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.ship.ShipInConstructionResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.ship.ShipInConstruction;
import restwars.service.ship.ShipService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import java.util.List;

public class ShipInConstructionSubResource {
    private final ShipService shipService;
    private final PlanetService planetService;

    @Inject
    public ShipInConstructionSubResource(ShipService shipService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
    }

    @GET
    public List<ShipInConstructionResponse> getShipsInConstruction(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        List<ShipInConstruction> shipsInConstruction = shipService.findShipsInConstructionOnPlanet(planet);
        return Helper.mapToList(shipsInConstruction, ShipInConstructionResponse::fromShipInConstruction);
    }
}
