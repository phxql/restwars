package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.model.event.EventWithPlanet;
import restwars.model.player.Player;
import restwars.rest.mapper.EventMapper;
import restwars.restapi.dto.event.EventsResponse;
import restwars.service.event.EventService;
import restwars.util.Functional;

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
     * @param max    Maximum number of events. Must be at least 1.
     * @return All events since the given round.
     */
    @GET
    @ApiOperation("Lists all events since a round")
    public EventsResponse getEvents(
            @Auth @ApiParam(access = "internal") Player player,
            @QueryParam("since") @ApiParam(value = "Round (inclusive)") @DefaultValue("1") long round,
            @QueryParam("max") @ApiParam(value = "Maximum number of events") Integer max
    ) {
        Preconditions.checkNotNull(player, "player");
        round = Math.max(1, round);

        List<EventWithPlanet> events;
        if (max == null) {
            events = eventService.findSince(player.getId(), round);
        } else {
            if (max < 1) {
                throw new ParameterValueWebException("max must be at least 1");
            }

            events = eventService.findSinceMax(player.getId(), round, max);
        }

        return new EventsResponse(Functional.mapToList(events, EventMapper::fromEvent));
    }

}
