package restwars.storage.ship;

import com.google.common.base.Preconditions;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.ship.ShipType;
import restwars.service.ship.ShipInConstruction;
import restwars.service.ship.ShipInConstructionDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.ShipInConstructionRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.SHIP_IN_CONSTRUCTION;

public class JooqShipInConstructionDAO extends AbstractJooqDAO implements ShipInConstructionDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqShipInConstructionDAO.class);

    @Inject
    public JooqShipInConstructionDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        context()
                .insertInto(
                        SHIP_IN_CONSTRUCTION, SHIP_IN_CONSTRUCTION.ID, SHIP_IN_CONSTRUCTION.TYPE,
                        SHIP_IN_CONSTRUCTION.PLANET_ID, SHIP_IN_CONSTRUCTION.PLAYER_ID, SHIP_IN_CONSTRUCTION.STARTED,
                        SHIP_IN_CONSTRUCTION.DONE
                )
                .values(
                        shipInConstruction.getId(), shipInConstruction.getType().getId(), shipInConstruction.getPlanetId(),
                        shipInConstruction.getPlayerId(), shipInConstruction.getStarted(), shipInConstruction.getDone()
                )
                .execute();
    }

    @Override
    public List<ShipInConstruction> findWithDone(long round) {
        LOGGER.debug("Finding ships in construction with done {}", round);

        Result<ShipInConstructionRecord> result = context().selectFrom(SHIP_IN_CONSTRUCTION).where(SHIP_IN_CONSTRUCTION.DONE.eq(round)).fetch();
        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    @Override
    public List<ShipInConstruction> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        LOGGER.debug("Finding ships in construction with planet id {}", planetId);

        Result<ShipInConstructionRecord> result = context().selectFrom(SHIP_IN_CONSTRUCTION).where(SHIP_IN_CONSTRUCTION.PLANET_ID.eq(planetId)).fetch();
        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    private ShipInConstruction fromRecord(ShipInConstructionRecord record) {
        assert record != null;

        return new ShipInConstruction(
                record.getId(), ShipType.fromId(record.getType()), record.getPlanetId(), record.getPlayerId(),
                record.getStarted(), record.getDone()
        );
    }

    @Override
    public void delete(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        LOGGER.debug("Deleting ship in construction {}", shipInConstruction);

        context().delete(SHIP_IN_CONSTRUCTION).where(SHIP_IN_CONSTRUCTION.ID.eq(shipInConstruction.getId())).execute();
    }
}
