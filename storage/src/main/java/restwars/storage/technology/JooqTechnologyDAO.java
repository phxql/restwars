package restwars.storage.technology;

import com.google.common.base.Preconditions;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.technology.Technology;
import restwars.service.technology.TechnologyDAO;
import restwars.service.technology.TechnologyType;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.TechnologyRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.TECHNOLOGY;

public class JooqTechnologyDAO extends AbstractJooqDAO implements TechnologyDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqTechnologyDAO.class);

    @Inject
    public JooqTechnologyDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public List<Technology> findAllWithPlayerId(UUID playerId) {
        Preconditions.checkNotNull(playerId, "playerId");

        LOGGER.debug("Finding technologies with player id {}", playerId);

        Result<TechnologyRecord> record = context().selectFrom(TECHNOLOGY).where(TECHNOLOGY.PLAYER_ID.eq(playerId)).fetch();
        return record.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    private Technology fromRecord(TechnologyRecord record) {
        assert record != null;

        return new Technology(record.getId(), TechnologyType.fromId(record.getType()), record.getLevel(), record.getPlayerId());
    }

    @Override
    public Optional<Technology> findWithPlayerId(UUID playerId, TechnologyType type) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(type, "type");

        LOGGER.debug("Finding technology with player id {} and type {}", playerId, type);

        TechnologyRecord record = context().selectFrom(TECHNOLOGY).where(TECHNOLOGY.PLAYER_ID.eq(playerId).and(TECHNOLOGY.TYPE.eq(type.getId()))).fetchOne();
        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(fromRecord(record));
    }

    @Override
    public void update(Technology technology) {
        Preconditions.checkNotNull(technology, "technology");

        LOGGER.debug("Updating technology {}", technology);

        context().update(TECHNOLOGY)
                .set(TECHNOLOGY.TYPE, technology.getType().getId())
                .set(TECHNOLOGY.PLAYER_ID, technology.getPlayerId())
                .set(TECHNOLOGY.LEVEL, technology.getLevel())
                .where(TECHNOLOGY.ID.eq(technology.getId()))
                .execute();
    }

    @Override
    public void insert(Technology technology) {
        Preconditions.checkNotNull(technology, "technology");

        LOGGER.debug("Inserting technology {}", technology);

        context()
                .insertInto(TECHNOLOGY, TECHNOLOGY.ID, TECHNOLOGY.TYPE, TECHNOLOGY.PLAYER_ID, TECHNOLOGY.LEVEL)
                .values(technology.getId(), technology.getType().getId(), technology.getPlayerId(), technology.getLevel())
                .execute();
    }
}
