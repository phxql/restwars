package restwars.storage.building;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.Buildings;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.planet.Location;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.mapper.BuildingMapper;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.BUILDING;
import static restwars.storage.jooq.Tables.PLANET;

public class JooqBuildingDAO extends AbstractJooqDAO implements BuildingDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqBuildingDAO.class);

    @Inject
    public JooqBuildingDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public Buildings findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        LOGGER.debug("Finding buildings with planet id {}", planetId);

        Result<Record> result = context().select().from(BUILDING).where(BUILDING.PLANET_ID.eq(planetId)).fetch();
        return new Buildings(result.stream().map(BuildingMapper::fromRecord).collect(Collectors.toList()));
    }

    @Override
    public void insert(Building building) {
        Preconditions.checkNotNull(building, "building");

        LOGGER.debug("Inserting building {}", building);

        context().insertInto(
                BUILDING, BUILDING.ID, BUILDING.TYPE, BUILDING.LEVEL, BUILDING.PLANET_ID
        ).values(
                building.getId(), building.getType().getId(), building.getLevel(), building.getPlanetId()
        ).execute();
    }

    @Override
    public void update(Building building) {
        Preconditions.checkNotNull(building, "building");

        LOGGER.debug("Updating building {}", building);

        context().update(BUILDING)
                .set(BUILDING.TYPE, building.getType().getId())
                .set(BUILDING.LEVEL, building.getLevel())
                .set(BUILDING.PLANET_ID, building.getPlanetId())
                .where(BUILDING.ID.eq(building.getId()))
                .execute();
    }

    @Override
    public Optional<Building> findWithPlanetLocationAndType(Location location, BuildingType type) {
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(type, "type");

        LOGGER.debug("Finding building with planet location {} and type {}", location, type);

        Record record = context().select().from(BUILDING)
                .join(PLANET).on(PLANET.ID.eq(BUILDING.PLANET_ID))
                .where(BUILDING.TYPE.eq(type.getId()))
                .and(PLANET.LOCATION_GALAXY.eq(location.getGalaxy()))
                .and(PLANET.LOCATION_PLANET.eq(location.getPlanet()))
                .and(PLANET.LOCATION_SOLAR_SYSTEM.eq(location.getSolarSystem()))
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(BuildingMapper.fromRecord(record));
    }

    @Override
    public Optional<Building> findWithPlanetIdAndType(UUID planetId, BuildingType type) {
        Preconditions.checkNotNull(planetId, "planetId");
        Preconditions.checkNotNull(type, "type");

        LOGGER.debug("Finding building with planet id {} and type {}", planetId, type);

        Record record = context().select().from(BUILDING).where(BUILDING.PLANET_ID.eq(planetId).and(BUILDING.TYPE.eq(type.getId()))).fetchOne();
        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(BuildingMapper.fromRecord(record));
    }
}
