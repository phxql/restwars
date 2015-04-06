package restwars.storage.points;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.points.Points;
import restwars.service.points.PointsDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Inject;

import static restwars.storage.jooq.Tables.POINTS;

/**
 * JOOQ based DAO for points.
 */
public class JooqPointsDAO extends AbstractJooqDAO implements PointsDAO {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqPointsDAO.class);

    /**
     * Constructor.
     *
     * @param unitOfWorkService Unit of work service.
     */
    @Inject
    public JooqPointsDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Points points) {
        Preconditions.checkNotNull(points, "points");

        LOGGER.debug("Inserting points {}", points);

        context().insertInto(POINTS, POINTS.ID, POINTS.PLAYER_ID, POINTS.ROUND, POINTS.POINTS_)
                .values(points.getId(), points.getPlayerId(), points.getRound(), points.getPoints())
                .execute();
    }
}
