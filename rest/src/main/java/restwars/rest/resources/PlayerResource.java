package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.planet.PlanetResponse;
import restwars.rest.api.player.PlayerResponse;
import restwars.rest.api.player.RegisterPlayerRequest;
import restwars.rest.util.Helper;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/v1/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {
    private final PlayerService playerService;
    private final PlanetService planetService;
    private final UnitOfWorkService unitOfWorkService;

    @Context
    private UriInfo uriInfo;

    @Inject
    public PlayerResource(PlayerService playerService, PlanetService planetService, UnitOfWorkService unitOfWorkService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
        this.unitOfWorkService = unitOfWorkService; // TODO: Move this in an JAXRS interceptor
    }

    @GET
    public PlayerResponse me(@Auth Player player) {
        List<Planet> planets = planetService.findWithOwner(player);

        return new PlayerResponse(player.getUsername(), Helper.mapToList(planets, PlanetResponse::fromPlanet));
    }

    @POST
    public Response register(@Valid RegisterPlayerRequest registration) {
        Preconditions.checkNotNull(registration, "registration");

        // TODO: Move this in an JAXRS interceptor
        execute(() -> {
            playerService.createPlayer(registration.getUsername(), registration.getPassword());
        });

        return Response.created(uriInfo.getAbsolutePathBuilder().path("/").build()).build();
    }

    private void execute(Runnable consumer) {
        unitOfWorkService.start();
        try {
            consumer.run();
            unitOfWorkService.commit();
        } catch (Exception e) {
            unitOfWorkService.abort();
            throw new RuntimeException(e);
        }
    }
}
