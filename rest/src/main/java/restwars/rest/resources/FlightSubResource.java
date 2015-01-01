package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.ship.CreateFlightRequest;
import restwars.rest.api.ship.FlightResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.ship.Flight;
import restwars.service.ship.InvalidFlightException;
import restwars.service.ship.NotEnoughShipsException;
import restwars.service.ship.ShipService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

public class FlightSubResource {
    private final ShipService shipService;
    private final PlanetService planetService;

    @Inject
    public FlightSubResource(ShipService shipService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
    }

    @GET
    @Path("/own")
    public List<FlightResponse> getOwnFlights(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        List<Flight> flights = shipService.findFlightsStartedFromPlanet(planet);

        return Helper.mapToList(flights, FlightResponse::fromFlight);
    }

    @POST
    @Path("/to/{destination}")
    public FlightResponse createFlight(@Auth Player player, @PathParam("start") LocationParam start, @PathParam("destination") LocationParam destination, @Valid CreateFlightRequest body) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(start, "start");
        Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkNotNull(body, "body");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, start.getValue(), player);
        try {
            Flight flight = shipService.sendShipsToPlanet(
                    player, planet, destination.getValue(), body.getParsedShips(), body.getParsedType(),
                    new Resources(body.getCargoCrystals(), body.getCargoGas(), body.getCargoEnergy())
            );

            return FlightResponse.fromFlight(flight);
        } catch (NotEnoughShipsException e) {
            throw new NotEnoughShipsWebException();
        } catch (InvalidFlightException e) {
            throw new InvalidFlightsWebException();
        } catch (InsufficientResourcesException e) {
            throw new InsufficientResourcesWebException();
        }
    }

}
