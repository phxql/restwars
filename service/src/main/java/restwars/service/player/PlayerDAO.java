package restwars.service.player;

import com.google.common.base.Optional;

/**
 * DAO for players.
 */
public interface PlayerDAO {
    /**
     * Inserts a player.
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
