package restwars.service.telescope.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.UniverseConfiguration;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.flight.DetectedFlight;
import restwars.model.flight.DetectedFlightWithSender;
import restwars.model.flight.Flight;
import restwars.model.flight.FlightType;
import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.planet.PlanetWithOwner;
import restwars.model.player.Player;
import restwars.service.building.BuildingDAO;
import restwars.service.event.EventService;
import restwars.service.flight.DetectedFlightDAO;
import restwars.service.flight.FlightDAO;
import restwars.service.infrastructure.RandomNumberGenerator;
import restwars.service.infrastructure.RoundService;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.planet.PlanetDAO;
import restwars.service.telescope.ScanException;
import restwars.service.telescope.TelescopeService;
import restwars.util.MathExt;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TelescopeServiceImpl implements TelescopeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelescopeServiceImpl.class);
    private final PlanetDAO planetDAO;
    private final BuildingDAO buildingDAO;
    private final DetectedFlightDAO detectedFlightDAO;
    private final FlightDAO flightDAO;
    private final RoundService roundService;
    private final EventService eventService;
    private final RandomNumberGenerator randomNumberGenerator;
    private final BuildingMechanics buildingMechanics;
    private final UniverseConfiguration universeConfiguration;

    @Inject
    public TelescopeServiceImpl(PlanetDAO planetDAO, BuildingDAO buildingDAO, DetectedFlightDAO detectedFlightDAO, FlightDAO flightDAO, RoundService roundService, RandomNumberGenerator randomNumberGenerator, EventService eventService, BuildingMechanics buildingMechanics, UniverseConfiguration universeConfiguration) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.detectedFlightDAO = Preconditions.checkNotNull(detectedFlightDAO, "detectedFlightDAO");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.eventService = Preconditions.checkNotNull(eventService, "eventService");
        this.randomNumberGenerator = Preconditions.checkNotNull(randomNumberGenerator, "randomNumberGenerator");
        this.buildingMechanics = Preconditions.checkNotNull(buildingMechanics, "buildingMechanics");
    }

    @Override
    public List<PlanetWithOwner> scan(Planet planet) throws ScanException {
        Preconditions.checkNotNull(planet, "planet");

        Optional<Building> telescope = buildingDAO.findWithPlanetId(planet.getId()).stream().filter(b -> b.getType().equals(BuildingType.TELESCOPE)).findFirst();
        if (!telescope.isPresent()) {
            throw new ScanException(ScanException.Reason.NO_TELESCOPE);
        }

        int level = telescope.get().getLevel();
        int scanRange = buildingMechanics.calculateScanRange(level);

        Location location = planet.getLocation();

        int galaxyMin = location.getGalaxy();
        int galaxyMax = location.getGalaxy();
        int solarSystemMin = Math.max(location.getSolarSystem() - scanRange, 1);
        int solarSystemMax = Math.min(location.getSolarSystem() + scanRange, universeConfiguration.getSolarSystemsPerGalaxy());
        int planetMin = 1;
        int planetMax = universeConfiguration.getPlanetsPerSolarSystem();

        Map<Location, PlanetWithOwner> foundPlanets = Maps.uniqueIndex(planetDAO.findInRange(galaxyMin, galaxyMax, solarSystemMin, solarSystemMax, planetMin, planetMax), PlanetWithOwner::getLocation);

        List<PlanetWithOwner> result = Lists.newArrayList();
        for (int g = galaxyMin; g <= galaxyMax; g++) {
            for (int s = solarSystemMin; s <= solarSystemMax; s++) {
                for (int p = planetMin; p <= planetMax; p++) {
                    Location currentLocation = new Location(g, s, p);

                    result.add(foundPlanets.getOrDefault(currentLocation, new PlanetWithOwner(currentLocation, Optional.<Planet>empty(), Optional.<Player>empty())));
                }
            }
        }
        return result;
    }

    @Override
    public List<DetectedFlightWithSender> findDetectedFlights(Player player) {
        Preconditions.checkNotNull(player, "player");
        LOGGER.debug("Finding detected flights for {}", player);

        return detectedFlightDAO.findWithPlayer(player.getId());
    }

    @Override
    public void detectFlights() {
        LOGGER.debug("Detecting flights");

        List<Flight> flights = flightDAO.findWithTypeAndDetected(FlightType.ATTACK, false);
        LOGGER.debug("Processing {} flights", flights.size());

        long currentRound = roundService.getCurrentRound();

        for (Flight flight : flights) {
            Optional<Building> telescope = buildingDAO.findWithPlanetLocationAndType(flight.getDestination(), BuildingType.TELESCOPE);
            if (telescope.isPresent()) {
                int range = MathExt.ceilInt(calculateFlightDetectionRange(telescope.get().getLevel()) * (1.0 / flight.getSpeed()));

                if (currentRound + range >= flight.getArrivalInRound()) {
                    detectFlight(flight, telescope.get().getLevel());
                }
            }
        }
    }

    private void detectFlight(Flight flight, int level) {
        LOGGER.debug("Detected flight {} with telescope level {}", flight, level);

        // Planet must exist, otherwise it couldn't have buildings on it
        Planet destinationPlanet = planetDAO.findWithLocation(flight.getDestination()).orElseThrow(AssertionError::new);

        // If the destination planet is owned from the attacker, don't create any events
        if (!destinationPlanet.getOwnerId().equals(flight.getPlayerId())) {
            double variance = calculateFleetSizeVariance(level);

            long realFleetSize = flight.getShips().amount();
            long delta = MathExt.floorLong(realFleetSize * variance);
            long approximatedFleetSize = Math.max(0, randomNumberGenerator.nextLong(realFleetSize - delta, realFleetSize + delta));

            LOGGER.trace("Real fleet size {}, variance {}, delta {}, approximated fleet size {}", realFleetSize, variance, delta, approximatedFleetSize);

            // Create detected flight
            DetectedFlight detectedFlight = new DetectedFlight(flight.getId(), destinationPlanet.getOwnerId(), approximatedFleetSize);
            detectedFlightDAO.insert(detectedFlight);

            eventService.createFlightDetectedEvent(flight.getPlayerId(), destinationPlanet.getOwnerId(), destinationPlanet.getId(), flight.getId(), detectedFlight.getFlightId());
        }

        // Update flight - it has been detected
        flightDAO.update(flight.withDetected(true));
    }

    @Override
    public int calculateFlightDetectionRange(int telescopeLevel) {
        Preconditions.checkArgument(telescopeLevel > 0, "telescopeLevel must be > 0");

        return buildingMechanics.calculateFlightDetectionRange(telescopeLevel);
    }

    @Override
    public double calculateFleetSizeVariance(int telescopeLevel) {
        Preconditions.checkArgument(telescopeLevel > 0, "telescopeLevel must be > 0");

        return buildingMechanics.calculateFleetSizeVariance(telescopeLevel);
    }
}
