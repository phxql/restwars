package restwars.storage.event;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.event.Event;
import restwars.model.event.EventType;
import restwars.service.event.EventWithPlanet;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.BasicScenario;
import restwars.storage.scenario.EventScenario;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JooqEventDAOTest extends DatabaseTest {
    private JooqEventDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqEventDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        Event event = new Event(UUID.fromString("e5a7d64d-56e0-4ada-8d0f-eef367d5dfcf"), BasicScenario.Player1.PLAYER.getId(),
                BasicScenario.Player1.Planet2.PLANET.getId(), EventType.FLIGHT_DETECTED, 1, Optional.<UUID>empty());
        sut.insert(event);

        List<Map<String, Object>> result = select("SELECT * FROM event WHERE id = ?", event.getId());
        assertThat(result, hasSize(1));
        verifyRow(result.get(0), event);
    }

    private void verifyRow(Map<String, Object> row, Event event) {
        assertThat(row.get("id"), is(event.getId()));
        assertThat(row.get("type"), is(event.getType().getId()));
        assertThat(row.get("fight_id"), is(nullValue()));
        assertThat(row.get("planet_id"), is(event.getPlanetId()));
        assertThat(row.get("player_id"), is(event.getPlayerId()));
        assertThat(row.get("round"), is(event.getRound()));
    }

    @Test
    public void testFindSince() throws Exception {
        List<EventWithPlanet> events = sut.findSince(EventScenario.Player1.EVENT.getPlayerId(), EventScenario.Player1.EVENT.getRound());

        assertThat(events, hasSize(1));
        assertThat(events.get(0), is(new EventWithPlanet(EventScenario.Player1.EVENT, BasicScenario.Player1.Planet1.PLANET)));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                EventScenario.create()
        );
    }
}