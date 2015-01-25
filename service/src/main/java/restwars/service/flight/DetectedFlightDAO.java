package restwars.service.flight;

import restwars.model.flight.DetectedFlight;
import restwars.model.flight.DetectedFlightWithSender;

import java.util.List;
import java.util.UUID;

/**
 * DAO for detected flights.
 */
public interface DetectedFlightDAO {
    /**
     * Inserts the given detected flight.
     *
     * @param detectedFlight Detected flight.
     */
    void insert(DetectedFlight detectedFlight);

    /**
     * Finds detected flights with the given player id.
     *
     * @param playerId Player id.
     * @return Detected flights.
     */
    List<DetectedFlightWithSender> findWithPlayer(UUID playerId);

    /**
     * Deletes the detected flight with the given id.
     *
     * @param id Id.
     */
    void delete(UUID id);
}
