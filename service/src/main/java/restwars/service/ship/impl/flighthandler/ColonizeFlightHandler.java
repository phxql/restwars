package restwars.service.ship.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.mechanics.PlanetMechanics;
import restwars.service.UniverseConfiguration;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.event.EventService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.ship.*;

import java.util.Map;
import java.util.Optional;

public class ColonizeFlightHandler extends AbstractFlightHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColonizeFlightHandler.class);

    private final UniverseConfiguration universeConfiguration;
    private final BuildingDAO buildingDAO;
    private final PlanetMechanics planetMechanics;

    public ColonizeFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, UniverseConfiguration universeConfiguration, EventService eventService, BuildingDAO buildingDAO, DetectedFlightDAO detectedFlightDAO, PlanetMechanics planetMechanics) {
        super(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventService, detectedFlightDAO);
        this.planetMechanics = Preconditions.checkNotNull(planetMechanics, "planetMechanics");
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

            Planet newPlanet = new Planet(getUuidFactory().create(), flight.getDestination(), flight.getPlayerId(), planetMechanics.getColonizedPlanetResources());
            getPlanetDAO().insert(newPlanet);

            // Create buildings on planet
            for (Map.Entry<BuildingType, Integer> building : planetMechanics.getColonizedPlanetBuildings().entrySet()) {
                Building entity = new Building(getUuidFactory().create(), building.getKey(), building.getValue(), newPlanet.getId());
                buildingDAO.insert(entity);
            }

            // Land the ships on the new planet
            Hangar hangar = getOrCreateHangar(newPlanet.getId(), flight.getPlayerId());
            hangar = hangar.withShips(flight.getShips().minus(ShipType.COLONY, 1));
            getHangarDAO().update(hangar);

            getDetectedFlightDAO().delete(flight.getId());
            getFlightDAO().delete(flight);

            // Create event
            getEventService().createPlanetColonizedEvent(flight.getPlayerId(), newPlanet.getId());
        }
    }
}
