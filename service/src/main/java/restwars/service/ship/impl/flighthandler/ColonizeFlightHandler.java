package restwars.service.ship.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.event.EventService;
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
    private final BuildingDAO buildingDAO;

    public ColonizeFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, UniverseConfiguration universeConfiguration, EventService eventService, BuildingDAO buildingDAO) {
        super(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventService);
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
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

            // Create a command center on the planet
            Building commandCenter = new Building(getUuidFactory().create(), BuildingType.COMMAND_CENTER, 1, newPlanet.getId());
            buildingDAO.insert(commandCenter);

            // Land the ships on the new planet
            Hangar hangar = getOrCreateHangar(newPlanet.getId(), flight.getPlayerId());
            Hangar updatedHangar = hangar.withShips(flight.getShips().minus(ShipType.COLONY, 1));
            getHangarDAO().update(updatedHangar);

            getFlightDAO().delete(flight);

            // Create event
            getEventService().createPlanetColonizedEvent(flight.getPlayerId(), newPlanet.getId());
        }
    }
}
