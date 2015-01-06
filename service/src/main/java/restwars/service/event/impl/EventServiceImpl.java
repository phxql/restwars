package restwars.service.event.impl;

import com.google.common.base.Preconditions;
import restwars.service.event.Event;
import restwars.service.event.EventDAO;
import restwars.service.event.EventService;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class EventServiceImpl implements EventService {
    private final EventDAO eventDAO;

    @Inject
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public List<Event> findSince(UUID playerId, long round) {
        Preconditions.checkNotNull(playerId, "playerId");

        return eventDAO.findSince(playerId, round);
    }
}
