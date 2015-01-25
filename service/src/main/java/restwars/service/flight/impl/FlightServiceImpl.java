package restwars.service.flight.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.mechanics.PlanetMechanics;
import restwars.mechanics.ShipMechanics;
import restwars.mechanics.TechnologyMechanics;
import restwars.service.UniverseConfiguration;
import restwars.service.building.BuildingDAO;
import restwars.service.event.EventService;
import restwars.service.fight.FightDAO;
import restwars.service.flight.*;
import restwars.service.flight.impl.flighthandler.AttackFlightHandler;
import restwars.service.flight.impl.flighthandler.ColonizeFlightHandler;
import restwars.service.flight.impl.flighthandler.TransferFlightHandler;
import restwars.service.flight.impl.flighthandler.TransportFlightHandler;
import restwars.service.infrastructure.RandomNumberGenerator;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.Resources;
import restwars.service.ship.*;
import restwars.service.ship.impl.ShipUtils;
import restwars.service.technology.Technologies;
import restwars.service.technology.TechnologyDAO;
import restwars.service.technology.TechnologyType;
import restwars.util.MathExt;

import javax.inject.Inject;
import java.util.List;

public class FlightServiceImpl implements FlightService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImpl.class);

    private final TransportFlightHandler transportFlightHandler;
    private final ColonizeFlightHandler colonizeFlightHandler;
    private final AttackFlightHandler attackFlightHandler;
    private final TransferFlightHandler transferFlightHandler;
    private final FlightDAO flightDAO;
    private final RoundService roundService;
    private final UniverseConfiguration universeConfiguration;
    private final TechnologyDAO technologyDAO;
    private final ShipMechanics shipMechanics;
    private final ShipUtils shipUtils;
    private final TechnologyMechanics technologyMechanics;
    private final HangarDAO hangarDAO;
    private final UUIDFactory uuidFactory;
    private final DetectedFlightDAO detectedFlightDAO;
    private final PlanetDAO planetDAO;
    private final EventService eventService;

    @Inject
    public FlightServiceImpl(RoundService roundService, TechnologyDAO technologyDAO, FlightDAO flightDAO, BuildingDAO buildingDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, EventService eventService, DetectedFlightDAO detectedFlightDAO, TechnologyMechanics technologyMechanics, ShipMechanics shipMechanics, UniverseConfiguration universeConfiguration, PlanetMechanics planetMechanics, FightDAO fightDAO, RandomNumberGenerator randomNumberGenerator) {
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.shipMechanics = Preconditions.checkNotNull(shipMechanics, "shipMechanics");
        this.technologyMechanics = Preconditions.checkNotNull(technologyMechanics, "technologyMechanics");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.detectedFlightDAO = Preconditions.checkNotNull(detectedFlightDAO, "detectedFlightDAO");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.eventService = Preconditions.checkNotNull(eventService, "eventService");

        transportFlightHandler = new TransportFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventService, detectedFlightDAO, shipMechanics);
        colonizeFlightHandler = new ColonizeFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, universeConfiguration, eventService, buildingDAO, detectedFlightDAO, planetMechanics, shipMechanics);
        attackFlightHandler = new AttackFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, fightDAO, eventService, randomNumberGenerator, detectedFlightDAO, shipMechanics);
        transferFlightHandler = new TransferFlightHandler(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventService, detectedFlightDAO, shipMechanics);
        shipUtils = new ShipUtils();
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
                    finishOutwardFlight(flight, round);
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

        // Start and destination must differ
        if (start.getLocation().equals(destination)) {
            throw new FlightException(FlightException.Reason.SAME_START_AND_DESTINATION);
        }
        // Check if destination is in the universe
        if (!destination.isValid(universeConfiguration.getPlanetsPerSolarSystem(), universeConfiguration.getSolarSystemsPerGalaxy(), universeConfiguration.getGalaxyCount())) {
            throw new FlightException(FlightException.Reason.INVALID_DESTINATION);
        }
        // Empty flights are forbidden
        if (ships.isEmpty()) {
            throw new FlightException(FlightException.Reason.NO_SHIPS);
        }
        // A colonize flight needs at least one colony ship
        if (flightType.equals(FlightType.COLONIZE) && ships.countByType(ShipType.COLONY) == 0) {
            throw new FlightException(FlightException.Reason.NO_COLONY_SHIP);
        }
        // Only transport, colonize and transfer flights can have cargo
        if (!cargo.isEmpty() && !(flightType.equals(FlightType.COLONIZE) || flightType.equals(FlightType.TRANSPORT) || flightType.equals(FlightType.TRANSFER))) {
            throw new FlightException(FlightException.Reason.NO_CARGO_ALLOWED);
        }
        // Energy can't be put in cargo
        if (cargo.containsEnergy()) {
            throw new FlightException(FlightException.Reason.CANT_CARGO_ENERGY);
        }

        Technologies technologies = technologyDAO.findAllWithPlayerId(player.getId());

        long distance = start.getLocation().calculateDistance(destination);
        double energyNeeded = 0;
        for (Ship ship : ships) {
            double shipModifier = shipMechanics.getFlightCostModifier(ship.getType());
            // TODO: Gameplay - This reaches eventually zero, fix this.
            double technologyModifier = (1 - technologyMechanics.calculateCombustionFlightCostReduction(technologies.getLevel(TechnologyType.COMBUSTION_ENGINE)));

            // This also contains the energy needed for the return flight
            energyNeeded += Math.max(1, shipModifier * distance * ship.getAmount() * technologyModifier * 2);
        }
        long totalEnergyNeeded = MathExt.ceilLong(energyNeeded);
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
        double speed = shipUtils.findSpeedOfSlowestShip(ships, shipMechanics);
        long started = roundService.getCurrentRound();
        long arrives = started + MathExt.ceilLong(distance / speed);

        // Decrease energy on start planet
        start = start.withResources(start.getResources().minus(Resources.energy(totalEnergyNeeded)));

        if (!cargo.isEmpty()) {
            // Check cargo space and resource availability
            if (cargo.sum() > shipUtils.calculateStorageCapacity(ships, shipMechanics)) {
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
        Flight flight = new Flight(uuidFactory.create(), start.getLocation(), destination, started, arrives, ships, totalEnergyNeeded, flightType, player.getId(), FlightDirection.OUTWARD, cargo, speed, false);
        flightDAO.insert(flight);
        return flight;
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

        detectedFlightDAO.delete(flight.getId());
        flightDAO.delete(flight);

        // Create event
        eventService.createFlightReturnedEvent(flight.getPlayerId(), planet.getId());
    }

    private void finishOutwardFlight(Flight flight, long round) {
        assert flight != null;

        LOGGER.debug("Finishing outward flight {}", flight);

        switch (flight.getType()) {
            case ATTACK:
                attackFlightHandler.handle(flight, round);
                break;
            case COLONIZE:
                colonizeFlightHandler.handle(flight, round);
                break;
            case TRANSPORT:
                transportFlightHandler.handle(flight, round);
                break;
            case TRANSFER:
                transferFlightHandler.handle(flight, round);
                break;
            default:
                throw new AssertionError("Unknown flight type: " + flight.getType());
        }
    }
}
