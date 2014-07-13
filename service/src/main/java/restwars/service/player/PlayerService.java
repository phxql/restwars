package restwars.service.player;

import com.google.common.base.Optional;

/**
 * Service to manage players.
 */
public interface PlayerService {
    /**
     * Creates a new player.
     *
     * @param username Username of the new player.
     * @param password Password of the new player.
     * @return Created player.
     */
    Player createPlayer(String username, String password);

    /**
     * Finds the player with the given username.
     *
     * @param username Username.
     * @return Player if found.
     */
    Optional<Player> findWithUsername(String username);
}
