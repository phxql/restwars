package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.event.EventResponse;
import restwars.service.event.Event;
import restwars.service.event.EventWithPlanet;
import restwars.service.planet.Planet;

public final class EventMapper {
    private EventMapper() {
    }

    public static EventResponse fromEvent(EventWithPlanet eventWithPlanet) {
        Preconditions.checkNotNull(eventWithPlanet, "eventWithPlanet");

        Event event = eventWithPlanet.getEvent();
        Planet planet = eventWithPlanet.getPlanet();
        return new EventResponse(planet.getLocation().toString(), event.getType().name(), event.getRound());
    }
}
