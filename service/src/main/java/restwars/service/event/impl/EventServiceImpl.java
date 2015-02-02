package restwars.service.event.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.event.Event;
import restwars.model.event.EventType;
import restwars.model.event.EventWithPlanet;
import restwars.service.event.EventDAO;
import restwars.service.event.EventService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventServiceImpl implements EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventDAO eventDAO;
    private final UUIDFactory uuidFactory;
    private final RoundService roundService;

    @Inject
    public EventServiceImpl(EventDAO eventDAO, UUIDFactory uuidFactory, RoundService roundService) {
        this.eventDAO = Preconditions.checkNotNull(eventDAO, "eventDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
    }

    @Override
    public List<EventWithPlanet> findSince(UUID playerId, long round) {
        Preconditions.checkNotNull(playerId, "playerId");
        LOGGER.debug("Finding events for player {} since round {}", playerId, round);

        return eventDAO.findSince(playerId, round);
    }

    @Override
    public Event createBuildingCompletedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating building completed event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.BUILDING_COMPLETED);
    }

    @Override
    public void createFightHappenedEvent(UUID attackerId, UUID defenderId, UUID planetId, UUID fightId) {
        Preconditions.checkNotNull(attackerId, "attackerId");
        Preconditions.checkNotNull(defenderId, "defenderId");
        Preconditions.checkNotNull(planetId, "planetId");
        Preconditions.checkNotNull(fightId, "fightId");
        LOGGER.debug("Creating fight happened event for attacker {}, defender {} on planet {} with fight {}", attackerId, defenderId, planetId, fightId);

        long round = roundService.getCurrentRound();

        Event attackerEvent = new Event(uuidFactory.create(), attackerId, planetId, EventType.FIGHT_HAPPENED, round, Optional.of(fightId));
        eventDAO.insert(attackerEvent);

        Event defenderEvent = new Event(uuidFactory.create(), defenderId, planetId, EventType.FIGHT_HAPPENED, round, Optional.of(fightId));
        eventDAO.insert(defenderEvent);
    }

    @Override
    public void createFlightDetectedEvent(UUID attackerId, UUID defenderId, UUID planetId, UUID flightId, UUID detectedFlightId) {
        Preconditions.checkNotNull(attackerId, "attackerId");
        Preconditions.checkNotNull(defenderId, "defenderId");
        Preconditions.checkNotNull(planetId, "planetId");
        Preconditions.checkNotNull(flightId, "fightId");
        Preconditions.checkNotNull(detectedFlightId, "detectedFlightId");
        LOGGER.debug("Creating flight detected event for attacker {}, defender {} om planet {} with flight {} and detected flight {}", attackerId, defenderId, planetId, flightId, detectedFlightId);

        long round = roundService.getCurrentRound();

        Event attackerEvent = new Event(uuidFactory.create(), attackerId, planetId, EventType.FLIGHT_HAS_BEEN_DETECTED, round, Optional.<UUID>empty());
        eventDAO.insert(attackerEvent);

        Event defenderEvent = new Event(uuidFactory.create(), defenderId, planetId, EventType.FLIGHT_DETECTED, round, Optional.<UUID>empty());
        eventDAO.insert(defenderEvent);
    }

    @Override
    public Event createTransportArrivedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating transport arrived event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.TRANSPORT_ARRIVED);
    }

    @Override
    public Event createPlanetColonizedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating planet colonized event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.PLANET_COLONIZED);
    }

    @Override
    public Event createShipCompletedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating ship completed event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.SHIP_COMPLETED);
    }

    @Override
    public Event createFlightReturnedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating flight returned event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.FLIGHT_RETURNED);
    }

    @Override
    public Event createTransferArrivedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating transfer arrived event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.TRANSFER_ARRIVED);
    }

    @Override
    public Event createResearchCompletedEvent(UUID playerId, UUID planetId) {
        Preconditions.checkNotNull(playerId, "playerId");
        Preconditions.checkNotNull(planetId, "planetId");
        LOGGER.debug("Creating research completed event for player {} on planet {}", playerId, planetId);

        return createSimpleEvent(playerId, planetId, EventType.RESEARCH_COMPLETED);
    }

    private Event createSimpleEvent(UUID playerId, UUID planetId, EventType type) {
        long round = roundService.getCurrentRound();
        Event event = new Event(uuidFactory.create(), playerId, planetId, type, round, Optional.<UUID>empty());
        eventDAO.insert(event);

        return event;
    }
}
