package restwars.service.ship.impl.flighthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.resource.Resources;
import restwars.service.ship.*;

import java.util.Optional;

public class ColonizeFlightHandler extends AbstractFlightHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColonizeFlightHandler.class);

    private final UniverseConfiguration universeConfiguration;

    public ColonizeFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, UniverseConfiguration universeConfiguration) {
        super(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory);
        this.universeConfiguration = universeConfiguration;
    }

    @Override
    public void handle(Flight flight, long round) {
        assert flight != null;
        LOGGER.debug("Finishing colonizing flight");

        Optional<Planet> planet = getPlanetDAO().findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            LOGGER.debug("Planet is already colonized, creating return flight");
            createReturnFlight(flight, flight.getShips(), flight.getCargo());
        } else {
            LOGGER.debug("Player {} colonized new planet at {}", flight.getPlayerId(), flight.getDestination());

            Planet newPlanet = new Planet(
                    getUuidFactory().create(), flight.getDestination(), flight.getPlayerId(),
                    // Store the remaining energy for the return flight and the cargo on the planet
                    universeConfiguration.getStartingResources().plus(Resources.energy(flight.getEnergyNeeded() / 2)).plus(flight.getCargo())
            );
            getPlanetDAO().insert(newPlanet);

            // Land the ships on the new planet
            Hangar hangar = getOrCreateHangar(newPlanet.getId(), flight.getPlayerId());
            Hangar updatedHangar = hangar.withShips(flight.getShips().minus(ShipType.COLONY, 1));
            getHangarDAO().update(updatedHangar);

            getFlightDAO().delete(flight);
        }
    }
}
