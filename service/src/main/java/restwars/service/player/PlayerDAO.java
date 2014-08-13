package restwars.service.player;

import restwars.service.unitofwork.UnitOfWork;

import java.util.Optional;

/**
 * DAO for players.
 */
public interface PlayerDAO {
    /**
     * Inserts the given player.
     *
     * @param uow    Unit of work.
     * @param player Player.
     */
    void insert(UnitOfWork uow, Player player);

    /**
     * Finds the player with the given username.
     *
     * @param uow      Unit of work.
     * @param username Username.
     * @return Player if found.
     */
    Optional<Player> findWithUsername(UnitOfWork uow, String username);
}
