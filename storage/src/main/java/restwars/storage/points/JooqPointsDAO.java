package restwars.storage.points;

import com.google.common.base.Preconditions;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.points.Points;
import restwars.service.points.PointsDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.PointsRecord;
import restwars.storage.mapper.PointsMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<Points> findPointsWithPlayerId(UUID playerId, int max) {
        Preconditions.checkNotNull(playerId, "playerId");

        LOGGER.debug("Finding max {} points for player {}", max, playerId);

        Result<PointsRecord> records = context().selectFrom(POINTS)
                .where(POINTS.PLAYER_ID.eq(playerId))
                .orderBy(POINTS.ROUND.desc())
                .limit(max)
                .fetch();

        return records.stream().map(PointsMapper::fromRecord).collect(Collectors.toList());
    }

    @Override
    public Optional<Points> findMostRecentPointsWithPlayerId(UUID playerId) {
        Preconditions.checkNotNull(playerId, "playerId");

        LOGGER.debug("Finding most recent points for player {}", playerId);

        PointsRecord record = context().selectFrom(POINTS)
                .where(POINTS.PLAYER_ID.eq(playerId))
                .orderBy(POINTS.ROUND.desc()).limit(1)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(PointsMapper.fromRecord(record));
    }
}
