package restwars.service.flight;

import restwars.model.flight.Flight;
import restwars.model.flight.FlightType;
import restwars.model.planet.Location;

import java.util.List;
import java.util.UUID;

/**
 * DAO for flights.
 */
public interface FlightDAO {
    /**
     * Inserts the given flight.
     *
     * @param flight Flight to insert.
     */
    void insert(Flight flight);

    /**
     * Finds all flights with the given player id.
     *
     * @param playerId Player id.
     * @return All flights with the given player id.
     */
    List<Flight> findWithPlayerId(UUID playerId);

    /**
     * Finds all flights with arrival in the given round.
     *
     * @param arrival Round of arrival.
     * @return All flights with arrival in the given round.
     */
    List<Flight> findWithArrival(long arrival);

    /**
     * Updates the given flight.
     *
     * @param flight Updated flight.
     */
    void update(Flight flight);

    /**
     * Deletes the given flight.
     *
     * @param flight Flight to delete.
     */
    void delete(Flight flight);

    /**
     * Finds all flights with the given start location.
     *
     * @param location Start location.
     * @return All flights with the given start location.
     */
    List<Flight> findWithStart(Location location);

    /**
     * Finds all flights with the given type.
     *
     * @param type     Flight type.
     * @param detected Detection status.
     * @return All flights with the given type.
     */
    List<Flight> findWithTypeAndDetected(FlightType type, boolean detected);
}
