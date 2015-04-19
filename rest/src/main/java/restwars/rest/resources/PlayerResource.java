package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.points.PlayerWithPoints;
import restwars.model.points.Points;
import restwars.rest.integration.authentication.PlayerAuthenticationCache;
import restwars.rest.mapper.PlanetMapper;
import restwars.rest.mapper.PointsMapper;
import restwars.restapi.dto.player.*;
import restwars.service.planet.PlanetService;
import restwars.service.player.CreatePlayerException;
import restwars.service.player.PlayerService;
import restwars.service.points.PointsService;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;

@Path("/v1/player")
@Api(value = "/v1/player", description = "Player management")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {
    private static final int MAX_POINTS_HISTORY = 100;

    private final PlayerService playerService;
    private final PlanetService planetService;
    private final PointsService pointsService;
    private final PlayerAuthenticationCache playerAuthenticationCache;

    @Context
    private UriInfo uriInfo;

    @Inject
    public PlayerResource(PlayerService playerService, PlanetService planetService, PlayerAuthenticationCache playerAuthenticationCache, PointsService pointsService) {
        this.playerAuthenticationCache = Preconditions.checkNotNull(playerAuthenticationCache, "playerAuthenticationCache");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
        this.pointsService = Preconditions.checkNotNull(pointsService, "pointsService");
    }

    @GET
    @ApiOperation(value = "Gets information about the current player", authorizations = {
            @Authorization("basicAuth")
    })
    public PlayerResponse me(@Auth @ApiParam(access = "internal") Player player) {
        List<Planet> planets = planetService.findWithOwner(player);
        Optional<Points> points = pointsService.getPointsForPlayer(player);

        return new PlayerResponse(
                player.getUsername(), Functional.mapToList(planets, PlanetMapper::fromPlanet),
                points.map(PointsMapper::fromPoints).orElse(new PointResponse(0, 0))
        );
    }

    @POST
    @ApiOperation("Registers a new player")
    public Response register(@Valid RegisterPlayerRequest registration) {
        Preconditions.checkNotNull(registration, "registration");

        try {
            playerService.createPlayer(registration.getUsername(), registration.getPassword());
            playerAuthenticationCache.invalidate(registration.getUsername(), registration.getPassword());

            return Response.created(uriInfo.getAbsolutePathBuilder().path("/").build()).build();
        } catch (CreatePlayerException e) {
            throw new CreatePlayerWebException(e, e.getReason());
        }
    }

    @GET
    @Path("/points")
    @ApiOperation(value = "Returns points over time for the current player", authorizations = {
            @Authorization("basicAuth")
    })
    public PointsResponse points(@Auth @ApiParam(access = "internal") Player player) {
        List<Points> points = pointsService.getPointsHistoryForPlayer(player, MAX_POINTS_HISTORY);

        return new PointsResponse(Functional.mapToList(points, PointsMapper::fromPoints));
    }

    @GET
    @Path("/ranking")
    @ApiOperation(value = "Returns the player ranking")
    public PlayerRankingResponse playerRanking(@QueryParam("max") @DefaultValue("100") int max) {
        if (max <= 0) {
            throw new ParameterValueWebException("Max must be > 0");
        }

        List<PlayerWithPoints> playerRanking = pointsService.fetchPlayerRanking(max);

        return new PlayerRankingResponse(Functional.mapToList(playerRanking,
                r -> new PlayerRankingEntryResponse(r.getPlayer().getUsername(), r.getPoints()))
        );
    }
}
