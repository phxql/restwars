package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.event.Event;
import restwars.service.event.EventDAO;
import restwars.service.event.EventType;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.Resources;
import restwars.service.ship.*;
import restwars.service.ship.impl.flighthandler.AttackFlightHandler;
import restwars.service.ship.impl.flighthandler.ColonizeFlightHandler;
import restwars.service.ship.impl.flighthandler.TransportFlightHandler;

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
    private final BuildingDAO buildingDAO;
    private final EventDAO eventDAO;

    private final TransportFlightHandler transportFlightHandler;
    private final ColonizeFlightHandler colonizeFlightHandler;
    private final AttackFlightHandler attackFlightHandler;
    private final ShipUtils shipUtils;

    @Inject
    public ShipServiceImpl(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService, FlightDAO flightDAO, UniverseConfiguration universeConfiguration, BuildingDAO buildingDAO, EventDAO eventDAO) {
        Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.shipInConstructionDAO = Preconditions.checkNotNull(shipInConstructionDAO, "shipInConstructionDAO");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.eventDAO = Preconditions.checkNotNull(eventDAO, "eventDAO");

        transportFlightHandler = new TransportFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory);
        colonizeFlightHandler = new ColonizeFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, universeConfiguration);
        attackFlightHandler = new AttackFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory);
        shipUtils = new ShipUtils();
    }

    @Override
    public List<ShipInConstruction> findShipsInConstructionOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return shipInConstructionDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws BuildShipException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        if (!hasShipyard(planet)) {
            throw new BuildShipException(BuildShipException.Reason.NO_SHIPYARD);
        }

        List<ShipInConstruction> shipsInConstruction = shipInConstructionDAO.findWithPlanetId(planet.getId());
        if (!shipsInConstruction.isEmpty()) {
            throw new BuildShipException(BuildShipException.Reason.NOT_ENOUGH_BUILD_QUEUES);
        }

        Resources buildCost = type.getBuildCost();
        if (!planet.getResources().isEnough(buildCost)) {
            throw new BuildShipException(BuildShipException.Reason.INSUFFICIENT_RESOURCES);
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().minus(buildCost));
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

        long round = roundService.getCurrentRound();
        List<Flight> flights = flightDAO.findWithArrival(round);

        for (Flight flight : flights) {
            switch (flight.getDirection()) {
                case OUTWARD:
                    finishOutwardFlight(flight);
                    break;
                case RETURN:
                    finishReturnFlight(flight, round);
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

        Hangar hangar = shipUtils.getOrCreateHangar(hangarDAO, uuidFactory, planet.getId(), player.getId());

        Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(ships));
        hangarDAO.update(updatedHangar);
    }

    private void finishReturnFlight(Flight flight, long round) {
        assert flight != null;
        LOGGER.debug("Finishing return flight {}", flight);

        // Land ships in hangar
        Planet planet = planetDAO.findWithLocation(flight.getStart()).get();
        Hangar hangar = shipUtils.getOrCreateHangar(hangarDAO, uuidFactory, planet.getId(), flight.getPlayerId());
        hangar = hangar.withShips(hangar.getShips().plus(flight.getShips()));
        hangarDAO.update(hangar);

        // Unload cargo
        planet = planet.withResources(planet.getResources().plus(flight.getCargo()));
        planetDAO.update(planet);

        flightDAO.delete(flight);

        // Create event
        eventDAO.insert(new Event(uuidFactory.create(), flight.getPlayerId(), planet.getId(), EventType.FLIGHT_RETURNED, round));
    }

    private void finishOutwardFlight(Flight flight) {
        assert flight != null;

        LOGGER.debug("Finishing outward flight {}", flight);

        switch (flight.getType()) {
            case ATTACK:
                attackFlightHandler.handle(flight);
                break;
            case COLONIZE:
                colonizeFlightHandler.handle(flight);
                break;
            case TRANSPORT:
                transportFlightHandler.handle(flight);
                break;
            default:
                throw new AssertionError("Unknown flight type: " + flight.getType());
        }
    }

    @Override
    public List<Flight> findFlightsForPlayer(Player player) {
        Preconditions.checkNotNull(player, "player");

        return flightDAO.findWithPlayerId(player.getId());
    }

    @Override
    public Flight sendShipsToPlanet(Player player, Planet start, Location destination, Ships ships, FlightType flightType, Resources cargo) throws FlightException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(start, "start");
        Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkNotNull(ships, "ships");
        Preconditions.checkNotNull(flightType, "flightType");
        Preconditions.checkNotNull(cargo, "cargo");

        // Empty flights are forbidden
        if (ships.isEmpty()) {
            throw new FlightException(FlightException.Reason.NO_SHIPS);
        }
        // A colonize flight needs at least one colony ship
        if (flightType.equals(FlightType.COLONIZE) && ships.countByType(ShipType.COLONY) == 0) {
            throw new FlightException(FlightException.Reason.NO_COLONY_SHIP);
        }
        // Only transport and colonize flights can have cargo
        if (!cargo.isEmpty() && !(flightType.equals(FlightType.COLONIZE) || flightType.equals(FlightType.TRANSPORT))) {
            throw new FlightException(FlightException.Reason.NO_CARGO_ALLOWED);
        }

        long distance = start.getLocation().calculateDistance(destination);
        double energyNeeded = 0;
        for (Ship ship : ships) {
            // This also contains the energy needed for the return flight
            energyNeeded += ship.getType().getFlightCostModifier() * distance * ship.getAmount() * 2;
        }
        long totalEnergyNeeded = (long) Math.ceil(energyNeeded);
        // Check if planet has enough energy
        if (!start.getResources().isEnoughEnergy(totalEnergyNeeded)) {
            throw new FlightException(FlightException.Reason.INSUFFICIENT_FUEL);
        }

        // Check if enough ships are on the start planet
        Hangar hangar = shipUtils.getOrCreateHangar(hangarDAO, uuidFactory, start.getId(), start.getOwnerId());
        for (Ship ship : ships) {
            if (hangar.getShips().countByType(ship.getType()) < ship.getAmount()) {
                throw new FlightException(FlightException.Reason.NOT_ENOUGH_SHIPS_ON_PLANET);
            }
        }

        // Calculate arrival time
        int speed = shipUtils.findSpeedOfSlowestShip(ships);
        long started = roundService.getCurrentRound();
        long arrives = started + (long) Math.ceil(distance / (double) speed);

        // Decrease energy on start planet
        start = start.withResources(start.getResources().minus(Resources.energy(totalEnergyNeeded)));

        if (!cargo.isEmpty()) {
            // Check cargo space and resource availability
            if (cargo.sum() > shipUtils.calculateStorageCapacity(ships)) {
                throw new FlightException(FlightException.Reason.NOT_ENOUGH_CARGO_SPACE);
            }
            if (!start.getResources().isEnough(cargo)) {
                throw new FlightException(FlightException.Reason.INSUFFICIENT_RESOURCES);
            }

            // Decrease resources
            start = start.withResources(start.getResources().minus(cargo));
        }
        planetDAO.update(start);

        // Remove ships from the planet
        Hangar updatedHangar = hangar.withShips(hangar.getShips().minus(ships));
        hangarDAO.update(updatedHangar);

        // Start the flight
        Flight flight = new Flight(uuidFactory.create(), start.getLocation(), destination, started, arrives, ships, totalEnergyNeeded, flightType, player.getId(), FlightDirection.OUTWARD, cargo);
        flightDAO.insert(flight);
        return flight;
    }

    @Override
    public void finishShipsInConstruction() {
        long round = roundService.getCurrentRound();

        List<ShipInConstruction> doneShips = shipInConstructionDAO.findWithDone(round);
        for (ShipInConstruction ship : doneShips) {
            Hangar hangar = shipUtils.getOrCreateHangar(hangarDAO, uuidFactory, ship.getPlanetId(), ship.getPlayerId());

            Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(ship.getType(), 1));
            hangarDAO.update(updatedHangar);
            shipInConstructionDAO.delete(ship);

            // Create event
            eventDAO.insert(new Event(uuidFactory.create(), ship.getPlayerId(), ship.getPlanetId(), EventType.SHIP_COMPLETED, round));
        }
    }
}
