package restwars.storage.planet;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.PLANET;

public class JooqPlanetDAO extends AbstractJooqDAO implements PlanetDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqPlanetDAO.class);

    @Inject
    public JooqPlanetDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        LOGGER.debug("Inserting planet {}", planet);

        context().insertInto(
                PLANET, PLANET.ID, PLANET.LOCATION_GALAXY, PLANET.LOCATION_SOLAR_SYSTEM, PLANET.LOCATION_PLANET,
                PLANET.OWNER_ID, PLANET.CRYSTALS, PLANET.GAS, PLANET.ENERGY
        ).values(
                planet.getId(), planet.getLocation().getGalaxy(), planet.getLocation().getSolarSystem(),
                planet.getLocation().getPlanet(), planet.getOwnerId().orElse(null), planet.getCrystals(),
                planet.getGas(), planet.getEnergy()
        ).execute();
    }

    @Override
    public List<Planet> findWithOwnerId(UUID ownerId) {
        Preconditions.checkNotNull(ownerId, "ownerId");

        LOGGER.debug("Finding planet with owner id {}", ownerId);

        Result<Record> result = context().select().from(PLANET).where(PLANET.OWNER_ID.eq(ownerId)).fetch();
        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    @Override
    public Optional<Planet> findWithLocation(Location location) {
        Preconditions.checkNotNull(location, "location");

        LOGGER.debug("Finding planet with location {}", location);

        Record record = context().select().from(PLANET).where(
                PLANET.LOCATION_GALAXY.eq(location.getGalaxy())
                        .and(PLANET.LOCATION_SOLAR_SYSTEM.eq(location.getSolarSystem())
                                        .and(PLANET.LOCATION_PLANET.eq(location.getPlanet()))
                        )).fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(fromRecord(record));
    }

    @Override
    public void update(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        LOGGER.debug("Updating planet {}", planet);

        context().update(PLANET)
                .set(PLANET.LOCATION_GALAXY, planet.getLocation().getGalaxy())
                .set(PLANET.LOCATION_SOLAR_SYSTEM, planet.getLocation().getSolarSystem())
                .set(PLANET.LOCATION_PLANET, planet.getLocation().getPlanet())
                .set(PLANET.OWNER_ID, planet.getOwnerId().orElse(null))
                .set(PLANET.CRYSTALS, planet.getCrystals())
                .set(PLANET.GAS, planet.getGas())
                .set(PLANET.ENERGY, planet.getEnergy())
                .where(PLANET.ID.eq(planet.getId()))
                .execute();

    }

    @Override
    public List<Planet> findAll() {
        LOGGER.debug("Finding all planets");

        return context().select().from(PLANET).fetch().stream().map(this::fromRecord).collect(Collectors.toList());
    }

    @Override
    public Optional<Planet> findWithId(UUID id) {
        Preconditions.checkNotNull(id, "id");

        LOGGER.debug("Finding planet with id {}", id);

        Record record = context().select().from(PLANET).where(PLANET.ID.eq(id)).fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(fromRecord(record));
    }

    private Planet fromRecord(Record record) {
        return new Planet(
                record.getValue(PLANET.ID), new Location(record.getValue(PLANET.LOCATION_GALAXY),
                record.getValue(PLANET.LOCATION_SOLAR_SYSTEM), record.getValue(PLANET.LOCATION_PLANET)),
                Optional.ofNullable(record.getValue(PLANET.OWNER_ID)), record.getValue(PLANET.CRYSTALS),
                record.getValue(PLANET.GAS), record.getValue(PLANET.ENERGY)
        );
    }
}
