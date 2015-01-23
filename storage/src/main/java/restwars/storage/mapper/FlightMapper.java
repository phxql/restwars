package restwars.storage.mapper;

import org.jooq.Record;
import restwars.service.planet.Location;
import restwars.service.resource.Resources;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightDirection;
import restwars.service.ship.FlightType;
import restwars.service.ship.Ships;

import java.util.UUID;

import static restwars.storage.jooq.Tables.FLIGHT;

/**
 * Maps records to Flights.
 */
public final class FlightMapper {
    private FlightMapper() {
    }

    /**
     * Maps a given record to a Flight. Doesn't include the flights ships.
     *
     * @param record Record.
     * @return Flight.
     */
    public static Flight fromRecordNoShips(Record record) {
        UUID id = record.getValue(FLIGHT.ID);
        Location start = new Location(record.getValue(FLIGHT.START_GALAXY), record.getValue(FLIGHT.START_SOLAR_SYSTEM), record.getValue(FLIGHT.START_PLANET));
        Location destination = new Location(record.getValue(FLIGHT.DESTINATION_GALAXY), record.getValue(FLIGHT.DESTINATION_SOLAR_SYSTEM), record.getValue(FLIGHT.DESTINATION_PLANET));
        long startedInRound = record.getValue(FLIGHT.STARTED_IN_ROUND);
        long arrivalInRound = record.getValue(FLIGHT.ARRIVAL_IN_ROUND);
        long energyNeeded = record.getValue(FLIGHT.ENERGY_NEEDED);
        FlightType type = FlightType.fromId(record.getValue(FLIGHT.TYPE));
        FlightDirection direction = FlightDirection.fromId(record.getValue(FLIGHT.DIRECTION));
        UUID recordPlayerId = record.getValue(FLIGHT.PLAYER_ID);
        long cargoCrystals = record.getValue(FLIGHT.CARGO_CRYSTALS);
        long cargoGas = record.getValue(FLIGHT.CARGO_GAS);
        double speed = record.getValue(FLIGHT.SPEED);
        boolean detected = record.getValue(FLIGHT.DETECTED);

        return new Flight(id, start, destination, startedInRound, arrivalInRound, Ships.EMPTY, energyNeeded, type, recordPlayerId, direction, new Resources(cargoCrystals, cargoGas, 0), speed, detected);
    }
}
