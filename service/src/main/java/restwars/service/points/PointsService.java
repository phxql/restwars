package restwars.service.points;

import restwars.model.player.Player;
import restwars.model.points.Points;

import java.util.List;
import java.util.Optional;

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
    Optional<Points> getPointsForPlayer(Player player);

    /**
     * Returns the points history for the given player.
     *
     * @param player Player.
     * @param max    Maximum number of points.
     * @return Points history.
     */
    List<Points> getPointsHistoryForPlayer(Player player, int max);
}
