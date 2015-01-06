package restwars.storage.event;

import com.google.common.base.Preconditions;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.event.Event;
import restwars.service.event.EventDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.EventRecord;
import restwars.storage.mapper.EventMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.EVENT;

public class JooqEventDAO extends AbstractJooqDAO implements EventDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqEventDAO.class);

    @Inject
    public JooqEventDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Event event) {
        Preconditions.checkNotNull(event, "event");

        LOGGER.debug("Inserting event {}", event);

        context()
                .insertInto(EVENT, EVENT.ID, EVENT.PLAYER_ID, EVENT.PLANET_ID, EVENT.TYPE, EVENT.ROUND)
                .values(event.getId(), event.getPlayerId(), event.getPlanetId(), event.getType().getId(), event.getRound())
                .execute();
    }

    @Override
    public List<Event> findSince(UUID playerId, long round) {
        Preconditions.checkNotNull(playerId, "playerId");

        LOGGER.debug("Finding all events for player {} since round {}", playerId, round);

        Result<EventRecord> result = context().selectFrom(EVENT).where(EVENT.ROUND.greaterOrEqual(round)).and(EVENT.ROUND.eq(round)).fetch();
        return result.stream().map(EventMapper::fromRecord).collect(Collectors.toList());
    }
}
