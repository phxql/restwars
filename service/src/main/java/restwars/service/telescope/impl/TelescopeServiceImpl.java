package restwars.service.telescope.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.infrastructure.RandomNumberGenerator;
import restwars.service.infrastructure.RoundService;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.ship.*;
import restwars.service.telescope.PlanetWithOwner;
import restwars.service.telescope.ScanException;
import restwars.service.telescope.TelescopeService;
import restwars.util.MathExt;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TelescopeServiceImpl implements TelescopeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelescopeServiceImpl.class);
    private final PlanetDAO planetDAO;
    private final BuildingDAO buildingDAO;
    private final DetectedFlightDAO detectedFlightDAO;
    private final FlightDAO flightDAO;
    private final RoundService roundService;
    private final RandomNumberGenerator randomNumberGenerator;

    @Inject
    public TelescopeServiceImpl(PlanetDAO planetDAO, BuildingDAO buildingDAO, DetectedFlightDAO detectedFlightDAO, FlightDAO flightDAO, RoundService roundService, RandomNumberGenerator randomNumberGenerator) {
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.detectedFlightDAO = Preconditions.checkNotNull(detectedFlightDAO, "detectedFlightDAO");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.randomNumberGenerator = Preconditions.checkNotNull(randomNumberGenerator, "randomNumberGenerator");
    }

    @Override
    public List<PlanetWithOwner> scan(Planet planet) throws ScanException {
        Preconditions.checkNotNull(planet, "planet");

        Optional<Building> telescope = buildingDAO.findWithPlanetId(planet.getId()).stream().filter(b -> b.getType().equals(BuildingType.TELESCOPE)).findFirst();
        if (!telescope.isPresent()) {
            throw new ScanException(ScanException.Reason.NO_TELESCOPE);
        }

        int level = telescope.get().getLevel();

        Location location = planet.getLocation();
        int delta = level - 1;
        return planetDAO.findInRange(location.getGalaxy(), location.getGalaxy(), location.getSolarSystem() - delta, location.getSolarSystem() + delta, 0, Integer.MAX_VALUE);
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

        List<Flight> flights = flightDAO.findWithType(FlightType.ATTACK);
        LOGGER.debug("Processing {} flights", flights.size());

        long currentRound = roundService.getCurrentRound();

        for (Flight flight : flights) {
            Optional<Building> telescope = buildingDAO.findWithPlanetLocationAndType(flight.getDestination(), BuildingType.TELESCOPE);
            if (telescope.isPresent()) {
                int range = calculateFlightDetectionRange(telescope.get().getLevel());

                // TODO: Include fleet speed, otherwise the fast ships are detected at a greater distance
                // TODO: Flights are detected multiple times
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

        double variance = calculateFleetSizeVariance(level);

        long realFleetSize = flight.getShips().amount();
        long delta = MathExt.floorLong(realFleetSize * variance);
        long approximatedFleetSize = randomNumberGenerator.nextLong(realFleetSize - delta, realFleetSize + delta);

        LOGGER.trace("Real fleet size {}, variance {}, delta {}, approximated fleet size {}", realFleetSize, variance, delta, approximatedFleetSize);

        DetectedFlight detectedFlight = new DetectedFlight(flight.getId(), destinationPlanet.getOwnerId(), approximatedFleetSize);
        detectedFlightDAO.insert(detectedFlight);

        // TODO: Insert event
    }

    @Override
    public int calculateFlightDetectionRange(int telescopeLevel) {
        Preconditions.checkArgument(telescopeLevel >= 0, "telescopeLevel must be >= 0");

        return telescopeLevel;
    }

    @Override
    public double calculateFleetSizeVariance(int telescopeLevel) {
        return Math.max(0.0, 1.0 - (telescopeLevel / 10.0));
    }
}
