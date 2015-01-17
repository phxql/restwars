package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.EventMapper;
import restwars.rest.util.Helper;
import restwars.restapi.dto.event.EventResponse;
import restwars.service.event.EventService;
import restwars.service.event.EventWithPlanet;
import restwars.service.player.Player;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Resource for events.
 */
@Path("/v1/event")
@Api(value = "/v1/event", description = "Events", authorizations = {
        @Authorization("basicAuth")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
    private final EventService eventService;

    @Inject
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Lists all events since a given round.
     *
     * @param player Player.
     * @param round  Round, inclusive.
     * @return All events since the given round.
     */
    @GET
    @ApiOperation("Lists all events since a round")
    public List<EventResponse> getEvents(
            @Auth @ApiParam(access = "internal") Player player,
            @QueryParam("since") @ApiParam(value = "Round (inclusive)", defaultValue = "1") long round
    ) {
        Preconditions.checkNotNull(player, "player");
        round = Math.max(1, round);

        List<EventWithPlanet> events = eventService.findSince(player.getId(), round);
        return Helper.mapToList(events, EventMapper::fromEvent);
    }

}
