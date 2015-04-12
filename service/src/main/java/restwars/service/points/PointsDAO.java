package restwars.service.points;

import restwars.model.points.Points;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for points.
 */
public interface PointsDAO {
    /**
     * Inserts the given points entity.
     *
     * @param points Points entity.
     */
    void insert(Points points);

    /**
     * Finds the most recent points for the player with the given id.
     *
     * @param playerId Id of the player.
     * @return Points.
     */
    Optional<Points> findMostRecentPointsWithPlayerId(UUID playerId);

    /**
     * Finds all points for the player with the given id.
     *
     * @param playerId Id of the player.
     * @param max      Maximum number of points.
     * @return Points.
     */
    List<Points> findPointsWithPlayerId(UUID playerId, int max);
}
