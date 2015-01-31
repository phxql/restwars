package restwars.storage.scenario;

import restwars.model.event.Event;
import restwars.model.event.EventType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventScenario extends AbstractFreemarkerScenario<EventScenario.Model> {
    public static class Player1 {
        public static Event EVENT = new Event(
                UUID.fromString("1fac2f3d-30a4-4da4-8478-4d8ee498d32a"), BasicScenario.Player1.PLAYER.getId(),
                BasicScenario.Player1.Planet1.PLANET.getId(), EventType.BUILDING_COMPLETED, 1, Optional.<UUID>empty()
        );
    }

    public static class Model {
        private final List<Event> events;

        public Model(List<Event> events) {
            this.events = events;
        }

        public List<Event> getEvents() {
            return events;
        }
    }

    private static final EventScenario INSTANCE = new EventScenario();

    public static EventScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<Event> events = Arrays.asList(
                Player1.EVENT
        );

        return new Model(events);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/event.sql.ftl";
    }
}
