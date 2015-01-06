package restwars.service.event;

import com.google.common.base.Objects;
import restwars.service.planet.Planet;

public class EventWithPlanet {
    private final Event event;
    private final Planet planet;

    public EventWithPlanet(Event event, Planet planet) {
        this.event = event;
        this.planet = planet;
    }

    public Event getEvent() {
        return event;
    }

    public Planet getPlanet() {
        return planet;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("event", event)
                .add("planet", planet)
                .toString();
    }
}
