package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.event.Event;
import restwars.model.event.EventType;
import restwars.model.event.EventWithPlanet;
import restwars.model.planet.Planet;
import restwars.restapi.dto.event.EventResponse;
import restwars.restapi.dto.metadata.EventTypeMetadataResponse;

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
        return new EventTypeMetadataResponse(eventType.name(), eventType.getDescription());
    }
}
