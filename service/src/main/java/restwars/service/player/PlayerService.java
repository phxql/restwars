package restwars.service.player;

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
}
