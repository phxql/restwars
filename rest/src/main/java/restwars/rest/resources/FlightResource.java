package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.FlightMapper;
import restwars.rest.util.Helper;
import restwars.restapi.dto.ship.FlightResponse;
import restwars.service.player.Player;
import restwars.service.ship.Flight;
import restwars.service.ship.ShipService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Resource for flights.
 */
@Path("/v1/flight")
@Api(value = "/v1/flight", description = "Flights", authorizations = {
        @Authorization("basicAuth")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlightResource {
    private final ShipService shipService;

    @Inject
    public FlightResource(ShipService shipService) {
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
    }

    /**
     * Lists all flights from the player.
     *
     * @param player Player.
     * @return All flights from the player.
     */
    @GET
    @Path("/own")
    @ApiOperation("Lists all own flights")
    public List<FlightResponse> ownFlights(@Auth @ApiParam(access = "internal") Player player) {
        Preconditions.checkNotNull(player, "player");

        List<Flight> flights = shipService.findFlightsForPlayer(player);

        return Helper.mapToList(flights, FlightMapper::fromFlight);
    }
}