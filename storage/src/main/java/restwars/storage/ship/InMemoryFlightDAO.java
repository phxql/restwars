package restwars.storage.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightDAO;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class InMemoryFlightDAO implements FlightDAO {
    private List<Flight> flights = Lists.newArrayList();

    @Override
    public void insert(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        flights.add(flight);
    }
}
