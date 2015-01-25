package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.FightMapper;
import restwars.restapi.dto.ship.FightResponse;
import restwars.service.fight.FightService;
import restwars.service.fight.FightWithPlanetAndPlayer;
import restwars.service.player.Player;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Resource for fights.
 */
@Path("/v1/fight")
@Api(value = "/v1/fight", description = "Fights", authorizations = {
        @Authorization("basicAuth")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FightResource {
    private final FightService fightService;

    @Inject
    public FightResource(FightService fightService) {
        this.fightService = Preconditions.checkNotNull(fightService, "fightService");
    }

    /**
     * Returns the fight with the given id.
     *
     * @param player Player.
     * @param id     Id of the fight.
     * @return Fight with the given id.
     */
    @GET
    @Path("/{id}")
    @ApiOperation("Reads a fight")
    public FightResponse getFight(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("id") @ApiParam(value = "Fight ID") UUID id
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(id, "id");

        Optional<FightWithPlanetAndPlayer> fight = fightService.findFight(id);
        if (!fight.isPresent()) {
            throw new FightNotFoundWebException();
        }

        // If the player isn't the attacker or the defender, access is denied
        if (!(player.is(fight.get().getAttacker()) || player.is(fight.get().getDefender()))) {
            throw new AccessDeniedWebException();
        }

        return FightMapper.fromFight(fight.get());
    }

    /**
     * Lists all fights where the player has participated since the given round.
     *
     * @param player Player.
     * @param round  Round, inclusive.
     * @return All fights where the player has participated since the given round.
     */
    @GET
    @Path("/own")
    @ApiOperation("Lists all own fights since a round")
    public List<FightResponse> ownFights(
            @Auth @ApiParam(access = "internal") Player player,
            @QueryParam("since") @ApiParam(value = "Round (inclusive)", defaultValue = "1") long round
    ) {
        Preconditions.checkNotNull(player, "player");
        round = Math.max(1, round);

        List<FightWithPlanetAndPlayer> fights = fightService.findFightsWithPlayerSinceRound(player, round);
        return Functional.mapToList(fights, FightMapper::fromFight);
    }
}
