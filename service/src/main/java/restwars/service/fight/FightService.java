package restwars.service.fight;

import restwars.service.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Manages fights.
 */
public interface FightService {
    /**
     * Finds all fights where the given player is either the attacker or the defender which happend since the given round.
     *
     * @param player Player.
     * @param round  Round (inclusive).
     * @return Fights.
     */
    List<FightWithPlanetAndPlayer> findFightsWithPlayerSinceRound(Player player, long round);

    /**
     * Finds the fight with the given id.
     *
     * @param id Id.
     * @return Fight, if found.
     */
    Optional<FightWithPlanetAndPlayer> findFight(UUID id);
}
