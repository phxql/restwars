package restwars.storage.event;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectSeekStep1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.event.Event;
import restwars.model.event.EventWithPlanet;
import restwars.service.event.EventDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.mapper.EventMapper;
import restwars.storage.mapper.PlanetMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.EVENT;
import static restwars.storage.jooq.Tables.PLANET;

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
                .insertInto(EVENT, EVENT.ID, EVENT.PLAYER_ID, EVENT.PLANET_ID, EVENT.TYPE, EVENT.ROUND, EVENT.FIGHT_ID)
                .values(event.getId(), event.getPlayerId(), event.getPlanetId(), event.getType().getId(), event.getRound(), event.getFightId().orElse(null))
                .execute();
    }

    @Override
    public List<EventWithPlanet> findSince(UUID playerId, long round) {
        Preconditions.checkNotNull(playerId, "playerId");

        LOGGER.debug("Finding all events for player {} since round {}", playerId, round);

        Result<Record> result = getFindSinceSql(playerId, round)
                .fetch();

        return result.stream().map(r -> new EventWithPlanet(EventMapper.fromRecord(r), PlanetMapper.fromRecord(r))).collect(Collectors.toList());
    }

    @Override
    public List<EventWithPlanet> findSinceMax(UUID playerId, long round, int max) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkArgument(max >= 0, "max must be >= 0");

        LOGGER.debug("Finding all events for player {} since round {}, maximum {}", playerId, round, max);

        Result<Record> result = getFindSinceSql(playerId, round)
                .limit(max)
                .fetch();

        return result.stream().map(r -> new EventWithPlanet(EventMapper.fromRecord(r), PlanetMapper.fromRecord(r))).collect(Collectors.toList());
    }

    private SelectSeekStep1<Record, Long> getFindSinceSql(UUID playerId, long round) {
        return context()
                .select().from(EVENT)
                .join(PLANET).on(PLANET.ID.eq(EVENT.PLANET_ID))
                .where(EVENT.PLAYER_ID.eq(playerId))
                .and(EVENT.ROUND.greaterOrEqual(round))
                .orderBy(EVENT.ROUND.desc());
    }
}
