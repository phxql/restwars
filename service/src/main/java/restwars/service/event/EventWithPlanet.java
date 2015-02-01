package restwars.service.event;

import com.google.common.base.Objects;
import restwars.model.event.Event;
import restwars.model.planet.Planet;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventWithPlanet that = (EventWithPlanet) o;

        return Objects.equal(this.event, that.event) &&
                Objects.equal(this.planet, that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(event, planet);
    }
}
