package restwars.service.fight;

import restwars.model.fight.Fight;
import restwars.model.fight.FightWithPlanetAndPlayer;

import java.util.List;
import java.util.Optional;
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
     * Finds the fight with the given id.
     *
     * @param id Id.
     * @return Fight, if found.
     */
    Optional<FightWithPlanetAndPlayer> findWithId(UUID id);

    /**
     * Finds all fights where the given player is either the attacker or the defender which happend since the given round.
     *
     * @param playerId Id of the player.
     * @param round    Round (inclusive).
     * @return Fights.
     */
    List<FightWithPlanetAndPlayer> findFightsWithPlayerSinceRound(UUID playerId, long round);
}
