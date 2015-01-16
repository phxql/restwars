package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.FightMapper;
import restwars.rest.util.Helper;
import restwars.restapi.dto.ship.FightResponse;
import restwars.service.player.Player;
import restwars.service.ship.FightWithPlanetAndPlayer;
import restwars.service.ship.ShipService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/v1/fight")
@Api(value = "/v1/fight", description = "Fights", authorizations = {
        @Authorization("basicAuth")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FightResource {
    private final ShipService shipService;

    @Inject
    public FightResource(ShipService shipService) {
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
    }

    @GET
    @Path("/{id}")
    @ApiOperation("Reads a fight")
    public FightResponse getFight(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("id") @ApiParam(value = "Fight ID") UUID id
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(id, "id");

        Optional<FightWithPlanetAndPlayer> fight = shipService.findFight(id);
        if (!fight.isPresent()) {
            throw new FightNotFoundWebException();
        }

        // If the player isn't the attacker or the defender, access is denied
        if (!(player.is(fight.get().getAttacker()) || player.is(fight.get().getDefender()))) {
            throw new AccessDeniedWebException();
        }

        return FightMapper.fromFight(fight.get());
    }

    @GET
    @Path("/own")
    @ApiOperation("Lists all own fights since a round")
    public List<FightResponse> ownFights(
            @Auth @ApiParam(access = "internal") Player player,
            @QueryParam("since") @ApiParam(value = "Round (inclusive)", defaultValue = "1") long round
    ) {
        Preconditions.checkNotNull(player, "player");
        round = Math.max(1, round);

        List<FightWithPlanetAndPlayer> fights = shipService.findFightsWithPlayerSinceRound(player, round);
        return Helper.mapToList(fights, FightMapper::fromFight);
    }
}
