package restwars.service.event;

import restwars.model.event.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {
    /**
     * Finds all events for a player which have occurred since the given round.
     *
     * @param playerId Id of the player.
     * @param round    Round (inclusive).
     * @return All events which have occurred since the given round.
     */
    List<EventWithPlanet> findSince(UUID playerId, long round);

    /**
     * Creates a research completed event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createResearchCompletedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a building completed event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createBuildingCompletedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a transfer arrived event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createTransferArrivedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a planet colonized event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createPlanetColonizedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a transport arrived event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createTransportArrivedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a flight returned event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createFlightReturnedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a ship completed event.
     *
     * @param playerId Id of the player.
     * @param planetId Id of the planet.
     * @return Created event.
     */
    Event createShipCompletedEvent(UUID playerId, UUID planetId);

    /**
     * Creates a transfer arrived event.
     *
     * @param attackerId Id of the attacker.
     * @param defenderId Id of the defender.
     * @param planetId   Id of the planet.
     * @param fightId    Id of the fight.
     */
    void createFightHappenedEvent(UUID attackerId, UUID defenderId, UUID planetId, UUID fightId);

    /**
     * Creates a flight detected event.
     *
     * @param attackerId       Id of the attacker.
     * @param defenderId       Id of the defender.
     * @param planetId         Id of the planet.
     * @param flightId         Id of the flight.
     * @param detectedFlightId Id of the detected flight.
     */
    void createFlightDetectedEvent(UUID attackerId, UUID defenderId, UUID planetId, UUID flightId, UUID detectedFlightId);
}
