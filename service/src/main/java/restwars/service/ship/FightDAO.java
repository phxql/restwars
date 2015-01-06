package restwars.service.ship;

import java.util.List;
import java.util.UUID;

/**
 * DAO for fights.
 */
public interface FightDAO {
    /**
     * Inserts the given fight.
     *
     * @param fight Fight to insert.
     */
    void insert(Fight fight);

    /**
     * Finds all fights where the given player is either the attacker or the defender which happend since the given round.
     *
     * @param playerId Id of the player.
     * @param round    Round (inclusive).
     * @return Fights.
     */
    List<FightWithPlanetAndPlayer> findFightsWithPlayerSinceRound(UUID playerId, long round);
}
