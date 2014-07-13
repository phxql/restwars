package restwars.rest.resources;

import com.google.common.base.Preconditions;
import restwars.rest.api.player.RegisterPlayer;
import restwars.service.player.PlayerService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v1/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {
    private final PlayerService playerService;

    @Context
    private UriInfo uriInfo;

    public PlayerResource(PlayerService playerService) {
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
    }

    @POST
    public Response register(@Valid RegisterPlayer registration) {
        Preconditions.checkNotNull(registration, "registration");

        playerService.createPlayer(registration.getUsername(), registration.getPassword());

        return Response.created(uriInfo.getAbsolutePathBuilder().path("/").build()).build();
    }
}
