package restwars.service.event;

import restwars.model.event.Event;
import restwars.model.event.EventWithPlanet;

import java.util.List;
import java.util.UUID;

public interface EventDAO {
    /**
     * Inserts the given event.
     *
     * @param event Event to insert.
     */
    void insert(Event event);

    /**
     * Finds all events for a player which have occurred since the given round.
     *
     * @param playerId Id of the player.
     * @param round    Round (inclusive).
     * @return All events which have occurred since the given round.
     */
    List<EventWithPlanet> findSince(UUID playerId, long round);

    /**
     * Finds all events for a player which have occurred since the given round. Returns a given maximum number of events.
     *
     * @param playerId Id of the player.
     * @param round    Round (inclusive).
     * @param max      Maximum number of events.
     * @return All events which have occurred since the given round.
     */
    List<EventWithPlanet> findSinceMax(UUID playerId, long round, int max);
}
