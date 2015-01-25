package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.FlightMapper;
import restwars.rest.mapper.ShipMapper;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.restapi.dto.ship.CreateFlightRequest;
import restwars.restapi.dto.ship.FlightResponse;
import restwars.service.flight.Flight;
import restwars.service.flight.FlightException;
import restwars.service.flight.FlightService;
import restwars.service.flight.FlightType;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.resource.Resources;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Subresource for flights to or from a planet.
 */
@Api(value = "/{location}/flight", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlightSubResource {
    private final FlightService flightService;
    private final PlanetService planetService;

    @Inject
    public FlightSubResource(PlanetService planetService, FlightService flightService) {
        this.flightService = Preconditions.checkNotNull(flightService, "flightService");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
    }

    /**
     * Lists all flights from the player to or from the given location.
     *
     * @param player   Player.
     * @param location Location.
     * @return All flights from the player to or from the given location.
     */
    @GET
    @Path("/own")
    @ApiOperation("Lists own flights from or to this planet")
    public List<FlightResponse> getOwnFlights(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        List<Flight> flights = flightService.findFlightsStartedFromPlanet(planet);

        return Helper.mapToList(flights, FlightMapper::fromFlight);
    }

    /**
     * Creates a new flight.
     *
     * @param player      Player.
     * @param start       Location of the start planet.
     * @param destination Location of the destination planet.
     * @param data        Information about the new flight.
     * @return Flight.
     */
    @POST
    @Path("/to/{destination}")
    @ApiOperation("Creates a flight")
    public FlightResponse createFlight(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Start planet") LocationParam start,
            @PathParam("destination") @ApiParam("Destination planet") LocationParam destination,
            @Valid CreateFlightRequest data
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(start, "start");
        Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkNotNull(data, "data");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, start.getValue(), player);
        try {
            Flight flight = flightService.sendShipsToPlanet(
                    player, planet, destination.getValue(), ShipMapper.fromShips(data.getShips()), FlightType.valueOf(data.getType()),
                    new Resources(data.getCargoCrystals(), data.getCargoGas(), 0)
            );

            return FlightMapper.fromFlight(flight);
        } catch (FlightException e) {
            throw new FlightWebException(e.getReason());
        }
    }

}
