package restwars.service.points;

import restwars.model.player.Player;

/**
 * Service for points.
 */
public interface PointsService {
    /**
     * Calculates the points for all players.
     */
    void calculatePointsForAllPlayers();

    /**
     * Returns the current points for the given player.
     *
     * @param player Player.
     * @return Points.
     */
    long getPointsForPlayer(Player player);
}
