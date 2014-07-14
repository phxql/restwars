package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.planet.PlanetDTO;
import restwars.rest.api.player.PlayerDTO;
import restwars.rest.api.player.RegisterPlayerDTO;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {
    private final PlayerService playerService;
    private final PlanetService planetService;

    @Context
    private UriInfo uriInfo;

    public PlayerResource(PlayerService playerService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
    }

    @GET
    public PlayerDTO me(@Auth Player player) {
        List<Planet> planets = planetService.findWithOwner(player);

        return new PlayerDTO(player.getUsername(), planets.stream().map(PlanetDTO::fromPlanet).collect(Collectors.toList()));
    }

    @POST
    public Response register(@Valid RegisterPlayerDTO registration) {
        Preconditions.checkNotNull(registration, "registration");

        playerService.createPlayer(registration.getUsername(), registration.getPassword());

        return Response.created(uriInfo.getAbsolutePathBuilder().path("/").build()).build();
    }
}
