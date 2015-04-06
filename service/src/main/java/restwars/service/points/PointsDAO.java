package restwars.service.points;

import restwars.model.points.Points;

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
}
