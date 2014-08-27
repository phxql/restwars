package restwars.storage.technology;

import com.google.common.base.Preconditions;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.technology.Research;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyType;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.ResearchRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.RESEARCH;

public class JooqResearchDAO extends AbstractJooqDAO implements ResearchDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqResearchDAO.class);

    @Inject
    public JooqResearchDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Research research) {
        Preconditions.checkNotNull(research, "research");

        LOGGER.debug("Inserting research {}", research);

        context()
                .insertInto(
                        RESEARCH, RESEARCH.ID, RESEARCH.TYPE, RESEARCH.LEVEL, RESEARCH.STARTED, RESEARCH.DONE,
                        RESEARCH.PLANET_ID, RESEARCH.PLAYER_ID
                )
                .values(
                        research.getId(), research.getType().getId(), research.getLevel(), research.getStarted(),
                        research.getDone(), research.getPlanetId(), research.getPlayerId()
                )
                .execute();
    }

    @Override
    public List<Research> findWithDone(long round) {
        LOGGER.debug("Finding researches with round {}", round);

        Result<ResearchRecord> result = context().selectFrom(RESEARCH).where(RESEARCH.DONE.eq(round)).fetch();
        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    private Research fromRecord(ResearchRecord record) {
        assert record != null;

        return new Research(
                record.getId(), TechnologyType.fromId(record.getType()), record.getLevel(), record.getStarted(),
                record.getDone(), record.getPlanetId(), record.getPlayerId()
        );
    }

    @Override
    public void delete(Research research) {
        Preconditions.checkNotNull(research, "research");

        LOGGER.debug("Deleting research {}", research);

        context().delete(RESEARCH).where(RESEARCH.ID.eq(research.getId())).execute();
    }

    @Override
    public List<Research> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        LOGGER.debug("Finding researches with planet id {}", planetId);

        Result<ResearchRecord> result = context().selectFrom(RESEARCH).where(RESEARCH.PLANET_ID.eq(planetId)).fetch();
        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }
}
