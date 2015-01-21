package restwars.storage.mapper;

import org.jooq.Record;
import restwars.service.ship.DetectedFlight;

import static restwars.storage.jooq.Tables.DETECTED_FLIGHT;

/**
 * Maps records to DetectedFlights.
 */
public final class DetectedFlightMapper {
    private DetectedFlightMapper() {
    }

    /**
     * Maps a given record to a DetectedFlight.
     *
     * @param record Record.
     * @return DetectedFlight.
     */
    public static DetectedFlight fromRecord(Record record) {
        return new DetectedFlight(
                record.getValue(DETECTED_FLIGHT.FLIGHT_ID), record.getValue(DETECTED_FLIGHT.PLAYER_ID),
                record.getValue(DETECTED_FLIGHT.APPROXIMATED_FLEET_SIZE)
        );
    }
}
