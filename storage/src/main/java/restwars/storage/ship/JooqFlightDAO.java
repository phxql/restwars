package restwars.storage.ship;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.planet.Location;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static restwars.storage.jooq.Tables.FLIGHT;

public class JooqFlightDAO extends AbstractJooqDAO implements FlightDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqFlightDAO.class);

    @Inject
    public JooqFlightDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        LOGGER.debug("Inserting flight {}", flight);

        // TODO: Remove the casts!
        context()
                .insertInto(
                        FLIGHT, FLIGHT.ID, FLIGHT.PLAYER_ID, FLIGHT.START_GALAXY, FLIGHT.START_SOLAR_SYSTEM, FLIGHT.START_PLANET,
                        FLIGHT.DESTINATION_GALAXY, FLIGHT.DESTINATION_SOLAR_SYSTEM, FLIGHT.DESTINATION_PLANET, FLIGHT.STARTED_IN_ROUND,
                        FLIGHT.ARRIVAL_IN_ROUND, FLIGHT.ENERGY_NEEDED, FLIGHT.TYPE, FLIGHT.DIRECTION
                )
                .values(
                        flight.getId(), flight.getPlayerId(), flight.getStart().getGalaxy(), flight.getStart().getSolarSystem(), flight.getStart().getPlanet(),
                        flight.getDestination().getGalaxy(), flight.getDestination().getSolarSystem(), flight.getDestination().getPlanet(),
                        (int) flight.getStartedInRound(), (int) flight.getArrivalInRound(), (int) flight.getEnergyNeeded(), flight.getType().getId(), flight.getDirection().getId()
                )
                .execute();
    }

    @Override
    public List<Flight> findWithPlayerId(UUID playerId) {
        throw new NotImplementedException();
    }

    @Override
    public List<Flight> findWithArrival(long arrival) {
        throw new NotImplementedException();
    }

    @Override
    public void update(Flight flight) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Flight flight) {
        throw new NotImplementedException();
    }

    @Override
    public List<Flight> findWithStart(Location location) {
        throw new NotImplementedException();
    }
}
