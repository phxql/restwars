package restwars.storage.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightDAO;

import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class InMemoryFlightDAO implements FlightDAO {
    private List<Flight> flights = Lists.newArrayList();

    @Override
    public void insert(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        flights.add(flight);
    }

    @Override
    public List<Flight> findWithPlayerId(UUID playerId) {
        Preconditions.checkNotNull(playerId, "playerId");

        return flights.stream().filter(f -> f.getPlayerId().equals(playerId)).collect(Collectors.toList());
    }
}
