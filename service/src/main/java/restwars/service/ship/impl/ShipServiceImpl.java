package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.ship.*;

import javax.inject.Inject;
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
    private final BuildingDAO buildingDAO;

    private final FightCalculator fightCalculator = new FightCalculator();

    @Inject
    public ShipServiceImpl(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService, FlightDAO flightDAO, UniverseConfiguration universeConfiguration, BuildingDAO buildingDAO) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.shipInConstructionDAO = Preconditions.checkNotNull(shipInConstructionDAO, "shipInConstructionDAO");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
    }

    @Override
    public List<ShipInConstruction> findShipsInConstructionOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return shipInConstructionDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws InsufficientResourcesException, InsufficientShipyardException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        if (!hasShipyard(planet)) {
            throw new InsufficientShipyardException(1);
        }

        // TODO: Check build queues

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

    private boolean hasShipyard(Planet planet) {
        List<Building> buildings = buildingDAO.findWithPlanetId(planet.getId());
        return buildings.stream().anyMatch(b -> b.getType().equals(BuildingType.SHIPYARD));
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
    public List<Flight> findFlightsStartedFromPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return flightDAO.findWithStart(planet.getLocation());
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

        UUID destinationPlanetId = planetDAO.findWithLocation(flight.getStart()).map(Planet::getId).get();
        Hangar hangar = getOrCreateHangar(destinationPlanetId, flight.getPlayerId());

        Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(flight.getShips()));
        hangarDAO.update(updatedHangar);

        flightDAO.delete(flight);
    }

    private void finishOutwardFlight(Flight flight) {
        assert flight != null;

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

        Optional<Planet> planet = planetDAO.findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            LOGGER.debug("Planet is already colonized, creating return flight");
            createReturnFlight(flight, flight.getShips());
        } else {
            LOGGER.debug("Player {} colonized new planet at {}", flight.getPlayerId(), flight.getDestination());

            Planet newPlanet = new Planet(uuidFactory.create(), flight.getDestination(), flight.getPlayerId(),
                    universeConfiguration.getStartingCrystals(), universeConfiguration.getStartingGas(),
                    universeConfiguration.getStartingEnergy() + flight.getEnergyNeeded() / 2);
            planetDAO.insert(newPlanet);

            // Land the ships on the new planet
            Hangar hangar = getOrCreateHangar(newPlanet.getId(), flight.getPlayerId());
            Hangar updatedHangar = hangar.withShips(flight.getShips().minus(ShipType.COLONY, 1));
            hangarDAO.update(updatedHangar);

            flightDAO.delete(flight);
        }
    }

    /**
     * Handles an attack flight.
     *
     * @param flight Flight to handle.
     */
    private void handleAttack(Flight flight) {
        assert flight != null;
        LOGGER.debug("Handling attack of flight {}", flight);

        Optional<Planet> planet = planetDAO.findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            Hangar hangar = getOrCreateHangar(planet.get().getId(), planet.get().getOwnerId());

            Fight fight = fightCalculator.attack(flight.getShips(), hangar.getShips());

            // Update defenders hangar
            hangarDAO.update(hangar.withShips(fight.getRemainingDefenderShips()));

            if (fight.getRemainingAttackerShips().isEmpty()) {
                LOGGER.debug("Attacker lost all ships");
                flightDAO.delete(flight);
            } else {
                if (fight.getRemainingDefenderShips().isEmpty()) {
                    // TODO: Loot planet
                }

                createReturnFlight(flight, fight.getRemainingAttackerShips());
            }
        } else {
            // Planet is not colonized, create return flight
            createReturnFlight(flight, flight.getShips());
        }
    }

    private void createReturnFlight(Flight flight, Ships ships) {
        assert flight != null;
        assert ships != null;

        // TODO: Calculate travel speed on the remaining ships
        long flightTime = flight.getArrivalInRound() - flight.getStartedInRound();
        long started = roundService.getCurrentRound();
        long arrival = started + flightTime;

        Flight returnFlight = new Flight(
                flight.getId(), flight.getStart(), flight.getDestination(),
                flight.getStartedInRound(), arrival, ships, flight.getEnergyNeeded(), flight.getType(), flight.getPlayerId(),
                FlightDirection.RETURN
        );
        flightDAO.update(returnFlight);

        LOGGER.debug("Created return flight {}", returnFlight);
    }

    @Override
    public List<Flight> findFlightsForPlayer(Player player) {
        Preconditions.checkNotNull(player, "player");

        return flightDAO.findWithPlayerId(player.getId());
    }

    @Override
    public Flight sendShipsToPlanet(Player player, Planet start, Location destination, Ships ships, FlightType flightType) throws NotEnoughShipsException, InvalidFlightException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(start, "start");
        Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkNotNull(ships, "ships");
        Preconditions.checkNotNull(flightType, "flightType");

        // Empty flights are forbidden
        if (ships.isEmpty()) {
            throw new InvalidFlightException(InvalidFlightException.Reason.NO_SHIPS);
        }
        // A colonize flight needs at least one colony ship
        if (flightType.equals(FlightType.COLONIZE) && ships.countByType(ShipType.COLONY) == 0) {
            throw new InvalidFlightException(InvalidFlightException.Reason.NO_COLONY_SHIP);
        }

        long distance = start.getLocation().calculateDistance(destination);
        double energyNeeded = 0;
        for (Ship ship : ships) {
            // This also contains the energy needed for the return flight
            energyNeeded += ship.getType().getFlightCostModifier() * distance * ship.getAmount() * 2;
        }
        long speed = findSpeedOfSlowestShip(ships);
        long started = roundService.getCurrentRound();
        long arrives = started + (long) Math.ceil(distance / speed);

        // TODO: Check if enough energy is available
        // TODO: Decrease energy

        // Check if enough ships are on the start planet
        Hangar hangar = hangarDAO.findWithPlanetId(start.getId()).orElseThrow(NotEnoughShipsException::new);
        for (Ship ship : ships) {
            if (hangar.getShips().countByType(ship.getType()) < ship.getAmount()) {
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
