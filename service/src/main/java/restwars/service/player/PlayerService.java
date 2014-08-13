package restwars.service.player;

import restwars.service.unitofwork.UnitOfWork;

import java.util.Optional;

/**
 * Service to manage players.
 */
public interface PlayerService {
    /**
     * Creates a new player.
     *
     * @param uow      Unit of work.
     * @param username Username of the new player.
     * @param password Password of the new player.
     * @return Created player.
     */
    Player createPlayer(UnitOfWork uow, String username, String password);

    /**
     * Finds the player with the given username.
     *
     * @param uow      Unit of work.
     * @param username Username.
     * @return Player if found.
     */
    Optional<Player> findWithUsername(UnitOfWork uow, String username);
}
