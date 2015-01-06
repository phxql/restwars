package restwars.service.event;

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
    List<Event> findSince(UUID playerId, long round);
}
