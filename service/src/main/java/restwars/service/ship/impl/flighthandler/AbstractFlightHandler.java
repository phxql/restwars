package restwars.service.ship.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.PlanetDAO;
import restwars.service.resource.Resources;
import restwars.service.ship.*;
import restwars.service.ship.impl.ShipUtils;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractFlightHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFlightHandler.class);

    private final RoundService roundService;
    private final FlightDAO flightDAO;
    private final PlanetDAO planetDAO;
    private final HangarDAO hangarDAO;
    private final UUIDFactory uuidFactory;
    private final ShipUtils shipUtils;

    public AbstractFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory) {
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.flightDAO = Preconditions.checkNotNull(flightDAO, "flightDAO");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");

        shipUtils = new ShipUtils();
    }

    protected ShipUtils getShipUtils() {
        return shipUtils;
    }

    protected void createReturnFlight(Flight flight, Ships ships, Resources cargo) {
        assert flight != null;
        assert ships != null;

        long distance = flight.getStart().calculateDistance(flight.getDestination());
        int speed = shipUtils.findSpeedOfSlowestShip(ships);
        long started = roundService.getCurrentRound();
        long arrival = started + (long) Math.ceil(distance / (double) speed);

        Flight returnFlight = new Flight(
                flight.getId(), flight.getStart(), flight.getDestination(),
                flight.getStartedInRound(), arrival, ships, flight.getEnergyNeeded(), flight.getType(), flight.getPlayerId(),
                FlightDirection.RETURN, cargo
        );
        flightDAO.update(returnFlight);
        LOGGER.debug("Created return flight {}", returnFlight);
    }

    protected Hangar getOrCreateHangar(UUID planetId, UUID playerId) {
        Optional<Hangar> mayBeHangar = hangarDAO.findWithPlanetId(planetId);
        Hangar hangar = mayBeHangar.orElse(new Hangar(uuidFactory.create(), planetId, playerId, Ships.EMPTY));

        if (!mayBeHangar.isPresent()) {
            hangarDAO.insert(hangar);
        }

        return hangar;
    }


    protected PlanetDAO getPlanetDAO() {
        return planetDAO;
    }

    protected FlightDAO getFlightDAO() {
        return flightDAO;
    }

    protected HangarDAO getHangarDAO() {
        return hangarDAO;
    }

    protected UUIDFactory getUuidFactory() {
        return uuidFactory;
    }

    public abstract void handle(Flight flight, long round);
}
