package restwars.rest.websocket;

import org.atmosphere.annotation.Suspend;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/")
public class WebsocketResource {
    @Suspend
    @GET
    public String registerForRoundEvent() {
        return "";
    }

    @Suspend
    @POST
    public String fooRegisterForRoundEvent() {
        return "";
    }
}
