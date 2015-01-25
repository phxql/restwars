package restwars.storage.ship;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.flight.DetectedFlight;
import restwars.service.flight.DetectedFlightDAO;
import restwars.service.flight.DetectedFlightWithSender;
import restwars.service.flight.Flight;
import restwars.service.player.Player;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.mapper.DetectedFlightMapper;
import restwars.storage.mapper.FlightMapper;
import restwars.storage.mapper.PlayerMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.*;

public class JooqDetectedFlightDAO extends AbstractJooqDAO implements DetectedFlightDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqDetectedFlightDAO.class);

    @Inject
    public JooqDetectedFlightDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(DetectedFlight detectedFlight) {
        Preconditions.checkNotNull(detectedFlight, "detectedFlight");
        LOGGER.debug("Inserting {}", detectedFlight);

        context().insertInto(DETECTED_FLIGHT, DETECTED_FLIGHT.FLIGHT_ID, DETECTED_FLIGHT.PLAYER_ID, DETECTED_FLIGHT.APPROXIMATED_FLEET_SIZE)
                .values(detectedFlight.getFlightId(), detectedFlight.getPlayerId(), detectedFlight.getApproximatedFleetSize())
                .execute();
    }

    @Override
    public List<DetectedFlightWithSender> findWithPlayer(UUID playerId) {
        Preconditions.checkNotNull(playerId, "playerId");
        LOGGER.debug("Finding detected flights from player {}", playerId);

        Result<Record> result = context().select().from(DETECTED_FLIGHT)
                .join(FLIGHT).on(FLIGHT.ID.eq(DETECTED_FLIGHT.FLIGHT_ID))
                .join(PLAYER).on(PLAYER.ID.eq(FLIGHT.PLAYER_ID))
                .where(DETECTED_FLIGHT.PLAYER_ID.eq(playerId))
                .fetch();

        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        Preconditions.checkNotNull(id, "id");
        LOGGER.debug("Deleting detected flight with id {}", id);

        context().delete(DETECTED_FLIGHT).where(DETECTED_FLIGHT.FLIGHT_ID.eq(id)).execute();
    }

    private DetectedFlightWithSender fromRecord(Record record) {
        DetectedFlight detectedFlight = DetectedFlightMapper.fromRecord(record);
        Flight flight = FlightMapper.fromRecordNoShips(record);
        Player sender = PlayerMapper.fromRecord(record);

        return new DetectedFlightWithSender(detectedFlight, flight, sender);
    }
}
