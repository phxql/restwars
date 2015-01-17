package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.event.EventResponse;
import restwars.restapi.dto.metadata.EventTypeMetadataResponse;
import restwars.service.event.Event;
import restwars.service.event.EventType;
import restwars.service.event.EventWithPlanet;
import restwars.service.planet.Planet;

import java.util.UUID;

/**
 * Maps event entities to DTOs and vice versa.
 */
public final class EventMapper {
    private EventMapper() {
    }

    public static EventResponse fromEvent(EventWithPlanet eventWithPlanet) {
        Preconditions.checkNotNull(eventWithPlanet, "eventWithPlanet");

        Event event = eventWithPlanet.getEvent();
        Planet planet = eventWithPlanet.getPlanet();
        return new EventResponse(planet.getLocation().toString(), event.getType().name(), event.getRound(), event.getFightId().map(UUID::toString).orElse(null));
    }

    public static EventTypeMetadataResponse fromEventType(EventType eventType) {
        return new EventTypeMetadataResponse(eventType.name());
    }
}
