package restwars.service.player;

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
}
