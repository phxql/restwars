package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.rest.util.Helper;
import restwars.restapi.dto.metadata.FlightTypeMetadataResponse;
import restwars.restapi.dto.ship.FlightResponse;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightType;

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

    public static FlightTypeMetadataResponse fromFlightType(FlightType flightType) {
        Preconditions.checkNotNull(flightType, "flightType");

        return new FlightTypeMetadataResponse(flightType.name(), flightType.getDescription());
    }
}
