package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.ship.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShipServiceImpl implements ShipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final HangarDAO hangarDAO;
    private final ShipInConstructionDAO shipInConstructionDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;
    private final FlightDAO flightDAO;
    private final UniverseConfiguration universeConfiguration;

    public ShipServiceImpl(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService, FlightDAO flightDAO, UniverseConfiguration universeConfiguration) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.shipInConstructionDAO = Preconditions.checkNotNull(shipInConstructionDAO, "shipInConstructionDAO");
    }

    @Override
    public List<ShipInConstruction> findShipsInConstructionOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return shipInConstructionDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws InsufficientResourcesException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        // TODO: Check build queues
        // TODO: Check if the planet has a shipyard

        Resources buildCost = type.getBuildCost();
        if (!planet.hasResources(buildCost)) {
            throw new InsufficientResourcesException(buildCost.getCrystals(), buildCost.getGas(), buildCost.getEnergy(), planet.getCrystals(), planet.getGas(), planet.getEnergy());
        }

        Planet updatedPlanet = planet.withResources(planet.getCrystals() - buildCost.getCrystals(), planet.getGas() - buildCost.getGas(), planet.getEnergy() - buildCost.getEnergy());
        planetDAO.update(updatedPlanet);

        UUID id = uuidFactory.create();
        long buildTime = type.getBuildTime();
        long currentRound = roundService.getCurrentRound();
        ShipInConstruction shipInConstruction = new ShipInConstruction(id, type, planet.getId(), player.getId(), currentRound, currentRound + buildTime);
        shipInConstructionDAO.insert(shipInConstruction);

        return shipInConstruction;
    }

    @Override
    public Ships findShipsOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        Optional<Hangar> hangar = hangarDAO.findWithPlanetId(planet.getId());
        if (hangar.isPresent()) {
            return hangar.get().getShips();
        } else {
            return Ships.EMPTY;
        }
    }

    @Override
    public void finishFlights() {
        LOGGER.trace("Enter: finishFlights()");

        List<Flight> flights = flightDAO.findWithArrival(roundService.getCurrentRound());

        for (Flight flight : flights) {
            switch (flight.getDirection()) {
                case OUTWARD:
                    finishOutwardFlight(flight);
                    break;
                case RETURN:
                    finishReturnFlight(flight);
                    break;
                default:
                    throw new AssertionError("Unknown flight direction value: " + flight.getDirection());
            }
        }

        LOGGER.trace("Leave: finishFlights()");
    }

    @Override
    public void manifestShips(Player player, Planet planet, Ships ships) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(ships, "ships");

        // TODO: Code duplication from finishShipsInConstruction, refactor!

        Hangar hangar = getOrCreateHangar(planet.getId(), player.getId());

        Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(ships));
        hangarDAO.update(updatedHangar);
    }

    private void finishReturnFlight(Flight flight) {
        assert flight != null;
        LOGGER.debug("Finishing return flight {}", flight);

        UUID destinationPlanetId = planetDAO.findWithLocation(flight.getDestination()).map(Planet::getId).get();
        Hangar hangar = getOrCreateHangar(destinationPlanetId, flight.getPlayerId());

        Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(flight.getShips()));
        hangarDAO.update(updatedHangar);

        flightDAO.delete(flight);
    }

    private void finishOutwardFlight(Flight flight) {
        assert flight != null;

        // TODO: Handle flight type, eg start fight

        LOGGER.debug("Finishing outward flight {}", flight);

        switch (flight.getType()) {
            case ATTACK:
                handleAttack(flight);
                break;
            case COLONIZE:
                handleColonize(flight);
                break;
            default:
                throw new AssertionError("Unknown flight type: " + flight.getType());
        }
    }

    private void handleColonize(Flight flight) {
        assert flight != null;
        LOGGER.debug("Finishing colonizing flight");

        // TODO: Check if the target planet is already colonized
        Optional<Planet> planet = planetDAO.findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            // TODO: The planet is already colonized, create return flight!
        } else {
            Planet newPlanet = new Planet(uuidFactory.create(), flight.getDestination(), Optional.of(flight.getPlayerId()),
                    universeConfiguration.getStartingCrystals(), universeConfiguration.getStartingGas(),
                    universeConfiguration.getStartingEnergy() + flight.getEnergyNeeded() / 2);

            planetDAO.insert(newPlanet);

            // Land the ships on the new planet
            Hangar hangar = getOrCreateHangar(newPlanet.getId(), flight.getPlayerId());
            Hangar updatedHangar = hangar.withShips(flight.getShips().minus(ShipType.COLONY, 1));
            hangarDAO.update(updatedHangar);
        }
    }

    // TODO: Refactor Map<ShipType, Long> and List<Ship> into a Ships class with useful methods to calculate with the ship numbers

    private void createReturnFlight(Flight flight, Ships ships) {
        assert flight != null;
        assert ships != null;

        // TODO: Calculate travel speed on the remaining ships
        long flightTime = flight.getArrivalInRound() - flight.getStartedInRound();
        long started = roundService.getCurrentRound();
        long arrival = started + flightTime;

        Flight returnFlight = new Flight(
                flight.getId(), flight.getDestination(), flight.getStart(),
                started, arrival, ships, flight.getEnergyNeeded(), flight.getType(), flight.getPlayerId(),
                FlightDirection.RETURN
        );
        flightDAO.update(returnFlight);

        LOGGER.debug("Created return flight {}", returnFlight);
    }

    /**
     * Handles an attack flight.
     *
     * @param flight Flight to handle.
     */
    private void handleAttack(Flight flight) {
        assert flight != null;

        createReturnFlight(flight, flight.getShips());
    }

    @Override
    public List<Flight> findFlightsForPlayer(Player player) {
        Preconditions.checkNotNull(player, "player");

        return flightDAO.findWithPlayerId(player.getId());
    }

    @Override
    public Flight sendShipsToPlanet(Player player, Planet start, Location destination, Ships ships, FlightType flightType) throws NotEnoughShipsException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(start, "start");
        Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkNotNull(ships, "ships");
        Preconditions.checkNotNull(flightType, "flightType");

        // TODO: Ensure that ships is not empty

        long distance = start.getLocation().calculateDistance(destination);
        double energyNeeded = 0;
        for (Ship ship : ships) {
            energyNeeded += ship.getType().getFlightCostModifier() * distance * ship.getCount();
        }
        long speed = findSpeedOfSlowestShip(ships);
        long started = roundService.getCurrentRound();
        long arrives = started + (long) Math.ceil(distance / speed);

        // TODO: Ensure that a colonization flight has a colony ship
        // TODO: Check if enough energy is available
        // TODO: Decrease energy
        // TODO: For attack flights the double amount of energy is needed, because of the return flight

        // Check if enough ships are on the start planet
        Hangar hangar = hangarDAO.findWithPlanetId(start.getId()).orElseThrow(NotEnoughShipsException::new);
        for (Ship ship : ships) {
            if (hangar.getShips().countByType(ship.getType()) < ship.getCount()) {
                throw new NotEnoughShipsException();
            }
        }

        // Remove ships from the planet
        Hangar updatedHangar = hangar.withShips(hangar.getShips().minus(ships));
        hangarDAO.update(updatedHangar);

        // Start the flight
        Flight flight = new Flight(uuidFactory.create(), start.getLocation(), destination, started, arrives, ships, (long) Math.ceil(energyNeeded), flightType, player.getId(), FlightDirection.OUTWARD);
        flightDAO.insert(flight);
        return flight;
    }

    private long findSpeedOfSlowestShip(Ships ships) {
        assert ships != null;

        return ships.asList().stream().map(s -> s.getType().getSpeed()).min(Long::compare).get();
    }

    @Override
    public void finishShipsInConstruction() {
        long round = roundService.getCurrentRound();

        List<ShipInConstruction> doneShips = shipInConstructionDAO.findWithDone(round);
        for (ShipInConstruction ship : doneShips) {
            Hangar hangar = getOrCreateHangar(ship.getPlanetId(), ship.getPlayerId());

            Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(ship.getType(), 1));
            hangarDAO.update(updatedHangar);
            shipInConstructionDAO.delete(ship);
        }
    }

    private Hangar getOrCreateHangar(UUID planetId, UUID playerId) {
        Optional<Hangar> mayBeHangar = hangarDAO.findWithPlanetId(planetId);
        Hangar hangar = mayBeHangar.orElse(new Hangar(uuidFactory.create(), planetId, playerId, Ships.EMPTY));

        if (!mayBeHangar.isPresent()) {
            hangarDAO.insert(hangar);
        }

        return hangar;
    }
}
