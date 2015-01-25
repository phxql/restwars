package restwars.storage.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.service.ship.Hangar;
import restwars.service.ship.HangarDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static restwars.storage.jooq.Tables.HANGAR;
import static restwars.storage.jooq.Tables.HANGAR_SHIPS;

public class JooqHangarDAO extends AbstractJooqDAO implements HangarDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqHangarDAO.class);

    @Inject
    public JooqHangarDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public Optional<Hangar> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        LOGGER.debug("Finding hangar for planet {}", planetId);

        Result<Record> result = context().select().from(HANGAR).leftOuterJoin(HANGAR_SHIPS).on(HANGAR_SHIPS.HANGAR_ID.eq(HANGAR.ID)).where(HANGAR.PLANET_ID.eq(planetId)).fetch();
        if (result.isEmpty()) {
            return Optional.empty();
        }

        Record firstRecord = result.get(0);

        UUID id = firstRecord.getValue(HANGAR.ID);
        UUID playerId = firstRecord.getValue(HANGAR.PLAYER_ID);

        Map<ShipType, Integer> ships = Maps.newHashMap();
        for (Record record : result) {
            Integer shipType = record.getValue(HANGAR_SHIPS.TYPE);
            Integer amount = record.getValue(HANGAR_SHIPS.AMOUNT);

            // Fields can be null because of the left outer join
            if (shipType != null && amount != null) {
                ships.put(ShipType.fromId(shipType), amount);
            }
        }

        return Optional.of(new Hangar(id, planetId, playerId, new Ships(ships)));
    }

    @Override
    public void update(Hangar hangar) {
        Preconditions.checkNotNull(hangar, "hangar");

        LOGGER.debug("Updating hangar {}", hangar);

        context()
                .update(HANGAR)
                .set(HANGAR.PLANET_ID, hangar.getPlanetId())
                .set(HANGAR.PLAYER_ID, hangar.getPlayerId())
                .where(HANGAR.ID.eq(hangar.getId()))
                .execute();

        context()
                .delete(HANGAR_SHIPS)
                .where(HANGAR_SHIPS.HANGAR_ID.eq(hangar.getId()))
                .execute();

        insertShips(hangar);
    }

    @Override
    public void insert(Hangar hangar) {
        Preconditions.checkNotNull(hangar, "hangar");

        LOGGER.debug("Inserting hangar {}", hangar);

        context()
                .insertInto(HANGAR, HANGAR.ID, HANGAR.PLANET_ID, HANGAR.PLAYER_ID)
                .values(hangar.getId(), hangar.getPlanetId(), hangar.getPlayerId())
                .execute();

        insertShips(hangar);
    }

    private void insertShips(Hangar hangar) {
        for (Ship ship : hangar.getShips()) {
            context()
                    .insertInto(HANGAR_SHIPS, HANGAR_SHIPS.HANGAR_ID, HANGAR_SHIPS.TYPE, HANGAR_SHIPS.AMOUNT)
                    .values(hangar.getId(), ship.getType().getId(), ship.getAmount())
                    .execute();
        }
    }
}
