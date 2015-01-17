package restwars.service.ship.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.event.EventService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.ship.Flight;
import restwars.service.ship.FlightDAO;
import restwars.service.ship.Hangar;
import restwars.service.ship.HangarDAO;

import java.util.Optional;

public class TransferFlightHandler extends AbstractFlightHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferFlightHandler.class);

    public TransferFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, EventService eventService) {
        super(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventService);
    }

    @Override
    public void handle(Flight flight, long round) {
        Preconditions.checkNotNull(flight, "flight");
        LOGGER.debug("Finishing transfer flight");

        Optional<Planet> planet = getPlanetDAO().findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            if (planet.get().getOwnerId().equals(flight.getPlayerId())) {
                LOGGER.debug("Transfer ships and cargo to planet {}", planet.get());

                // Transfer cargo
                Planet updatedPlanet = planet.get().withResources(planet.get().getResources().plus(flight.getCargo()));
                getPlanetDAO().update(updatedPlanet);

                // Land ships
                Hangar hangar = getOrCreateHangar(planet.get().getId(), flight.getPlayerId());
                hangar = hangar.withShips(hangar.getShips().plus(flight.getShips()));
                getHangarDAO().update(hangar);

                getFlightDAO().delete(flight);

                // Create event
                getEventService().createTransferArrivedEvent(flight.getPlayerId(), planet.get().getId());
            } else {
                LOGGER.debug("Tried to transfer to enemy planet {} , creating return flight", flight.getDestination());

                createReturnFlight(flight, flight.getShips(), flight.getCargo());
            }
        } else {
            LOGGER.debug("Planet {} isn't colonized, creating return flight", flight.getDestination());

            createReturnFlight(flight, flight.getShips(), flight.getCargo());
        }
    }
}
