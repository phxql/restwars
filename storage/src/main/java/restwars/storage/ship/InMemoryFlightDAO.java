package restwars.storage.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightDAO;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class InMemoryFlightDAO implements FlightDAO {
    private Map<UUID, Flight> flights = Maps.newHashMap();

    @Override
    public void insert(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        flights.put(flight.getId(), flight);
    }

    @Override
    public List<Flight> findWithPlayerId(UUID playerId) {
        Preconditions.checkNotNull(playerId, "playerId");

        return flights.values().stream().filter(f -> f.getPlayerId().equals(playerId)).collect(Collectors.toList());
    }

    @Override
    public List<Flight> findWithArrival(long arrival) {
        return flights.values().stream().filter(f -> f.getArrival() == arrival).collect(Collectors.toList());
    }

    @Override
    public void delete(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        flights.remove(flight.getId());
    }

    @Override
    public void update(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        flights.put(flight.getId(), flight);
    }
}
