package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.ship.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShipServiceImpl implements ShipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final HangarDAO hangarDAO;
    private final ShipInConstructionDAO shipInConstructionDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;
    private final FlightDAO flightDAO;

    public ShipServiceImpl(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService, FlightDAO flightDAO) {
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

        Resources buildCost = calculateBuildCost(type);
        if (!planet.hasResources(buildCost)) {
            throw new InsufficientResourcesException(buildCost.getCrystals(), buildCost.getGas(), buildCost.getEnergy(), planet.getCrystals(), planet.getGas(), planet.getEnergy());
        }

        Planet updatedPlanet = planet.withResources(planet.getCrystals() - buildCost.getCrystals(), planet.getGas() - buildCost.getGas(), planet.getEnergy() - buildCost.getEnergy());
        planetDAO.update(updatedPlanet);

        UUID id = uuidFactory.create();
        long buildTime = calculateBuildTime(type);
        long currentRound = roundService.getCurrentRound();
        ShipInConstruction shipInConstruction = new ShipInConstruction(id, type, planet.getId(), player.getId(), currentRound, currentRound + buildTime);
        shipInConstructionDAO.insert(shipInConstruction);

        return shipInConstruction;
    }

    @Override
    public long calculateBuildTime(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 1;
            default:
                throw new AssertionError("Unknown ship type: " + type);
        }
    }

    @Override
    public Resources calculateBuildCost(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return new Resources(1, 1, 1);
            default:
                throw new AssertionError("Unknown ship type: " + type);
        }
    }

    @Override
    public List<Ship> findShipsOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        Optional<Hangar> hangar = hangarDAO.findWithPlanetId(planet.getId());
        if (hangar.isPresent()) {
            return hangar.get().getShips().entrySet().stream().map(e -> new Ship(e.getKey(), e.getValue())).collect(Collectors.toList());
        } else {
            return Lists.newArrayList();
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

    private void finishReturnFlight(Flight flight) {
        assert flight != null;

        LOGGER.debug("Finishing return flight {}", flight);

        // TODO: Add ships to planet
        flightDAO.delete(flight);
    }

    private void finishOutwardFlight(Flight flight) {
        assert flight != null;

        // TODO: Handle flight type, eg start fight

        LOGGER.debug("Finishing outward flight {}", flight);

        long flightTime = flight.getArrival() - flight.getStarted();
        long started = roundService.getCurrentRound();
        long arrival = started + flightTime;

        Flight returnFlight = new Flight(
                flight.getId(), flight.getDestinationPlanetId(), flight.getStartPlanetId(),
                started, arrival, flight.getShips(), flight.getEnergyNeeded(), flight.getType(), flight.getPlayerId(),
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
    public Flight sendShipsToPlanet(Player player, Planet start, Planet destination, List<Ship> ships, FlightType flightType) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(start, "start");
        Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkNotNull(ships, "ships");
        Preconditions.checkNotNull(flightType, "flightType");

        long energyNeeded = 1; // TODO: Calculate energy cost
        long started = roundService.getCurrentRound();
        long arrives = started + 1; // TODO: Calculate flight duration

        // TODO: Check if enough ships are available
        // TODO: Decrease ships on planet

        Flight flight = new Flight(uuidFactory.create(), start.getId(), destination.getId(), started, arrives, ships, energyNeeded, flightType, player.getId(), FlightDirection.OUTWARD);
        flightDAO.insert(flight);
        return flight;
    }

    @Override
    public void finishShipsInConstruction() {
        long round = roundService.getCurrentRound();

        List<ShipInConstruction> doneShips = shipInConstructionDAO.findWithDone(round);
        for (ShipInConstruction ship : doneShips) {
            Optional<Hangar> mayBeHangar = hangarDAO.findWithPlanetId(ship.getPlanetId());
            Hangar hangar = mayBeHangar.orElse(new Hangar(uuidFactory.create(), ship.getPlanetId(), ship.getPlayerId(), Maps.newHashMap()));
            boolean insert = !mayBeHangar.isPresent();

            Long shipCount = hangar.getShips().get(ship.getType());
            if (shipCount == null) {
                shipCount = 0L;
            }
            shipCount += 1;
            hangar.getShips().put(ship.getType(), shipCount);

            if (insert) {
                hangarDAO.insert(hangar);
            } else {
                hangarDAO.update(hangar);
            }
            shipInConstructionDAO.delete(ship);
        }
    }
}
