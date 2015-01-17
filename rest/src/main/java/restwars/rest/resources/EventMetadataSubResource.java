package restwars.rest.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.rest.mapper.EventMapper;
import restwars.restapi.dto.metadata.EventTypeMetadataResponse;
import restwars.service.event.EventType;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Subresource for event metadata.
 */
@Api(value = "/event", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventMetadataSubResource {
    @Inject
    public EventMetadataSubResource() {
    }

    /**
     * Lists all event types.
     *
     * @return All event types.
     */
    @GET
    @Path("/type")
    @ApiOperation("Lists all event types")
    public List<EventTypeMetadataResponse> flightTypes() {
        return Stream.of(EventType.values()).map(EventMapper::fromEventType).collect(Collectors.toList());
    }
}
