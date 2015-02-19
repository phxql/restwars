package restwars.rest.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.jersey.SuspendResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WebsocketResource {
    /**
     * Name of the round broadcaster.
     */
    private static final String ROUND_BROADCASTER_NAME = "round";

    @Context
    private BroadcasterFactory broadcasterFactory;


    @Suspend
    @GET
    @Path("/round")
    public SuspendResponse<String> registerForRoundEvent(@Context AtmosphereResource resource) {
        return new SuspendResponse.SuspendResponseBuilder<String>()
                .resumeOnBroadcast(resource.transport() != AtmosphereResource.TRANSPORT.WEBSOCKET)
                .broadcaster(broadcasterFactory.lookup(DefaultBroadcaster.class, ROUND_BROADCASTER_NAME, true))
                .build();
    }

    private static class Round {
        private final long round;

        public Round(long round) {
            this.round = round;
        }

        public long getRound() {
            return round;
        }
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void broadcastRound(BroadcasterFactory broadcasterFactory, long round) {
        Round message = new Round(round);
        String json;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to convert round object to json", e);
        }

        broadcasterFactory.lookup(DefaultBroadcaster.class, ROUND_BROADCASTER_NAME, true).broadcast(json);
    }
}
