package restwars.service.player;

import restwars.model.player.Player;

import java.util.Optional;

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
     * @throws CreatePlayerException If the player couldn't be created.
     */
    Player createPlayer(String username, String password) throws CreatePlayerException;

    /**
     * Finds the player with the given username.
     *
     * @param username Username.
     * @return Player if found.
     */
    Optional<Player> findWithUsername(String username);
}
