package restwars.service.player;

import restwars.model.player.Player;

import java.util.List;
import java.util.Optional;

/**
 * DAO for players.
 */
public interface PlayerDAO {
    /**
     * Inserts the given player.
     *
     * @param player Player.
     */
    void insert(Player player);

    /**
     * Finds the player with the given username.
     *
     * @param username Username.
     * @return Player if found.
     */
    Optional<Player> findWithUsername(String username);

    /**
     * Returns all players.
     *
     * @return All players.
     */
    List<Player> findAll();
}
