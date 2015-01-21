package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.rest.util.Helper;
import restwars.restapi.dto.metadata.FlightTypeMetadataResponse;
import restwars.restapi.dto.ship.DetectedFlightResponse;
import restwars.restapi.dto.ship.FlightResponse;
import restwars.service.ship.DetectedFlightWithSender;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightType;

/**
 * Maps flight entities to DTOs and vice versa.
 */
public final class FlightMapper {
    private FlightMapper() {
    }

    public static FlightResponse fromFlight(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        return new FlightResponse(
                flight.getStart().toString(),
                flight.getDestination().toString(), flight.getStartedInRound(), flight.getArrivalInRound(),
                Helper.mapToList(flight.getShips().asList(), ShipMapper::fromShip), flight.getType().toString(),
                flight.getDirection().toString(), ResourcesMapper.fromResources(flight.getCargo())
        );
    }

    public static DetectedFlightResponse fromDetectedFlight(DetectedFlightWithSender flight) {
        Preconditions.checkNotNull(flight, "flight");

        return new DetectedFlightResponse(
                flight.getFlight().getStart().toString(), flight.getSender().getUsername(),
                flight.getFlight().getDestination().toString(), flight.getFlight().getArrivalInRound(),
                flight.getDetectedFlight().getApproximatedFleetSize()
        );
    }

    public static FlightTypeMetadataResponse fromFlightType(FlightType flightType) {
        Preconditions.checkNotNull(flightType, "flightType");

        return new FlightTypeMetadataResponse(flightType.name(), flightType.getDescription());
    }
}
