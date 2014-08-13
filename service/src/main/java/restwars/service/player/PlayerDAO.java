package restwars.service.player;

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
}
