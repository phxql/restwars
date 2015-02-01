package restwars.storage.ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.flight.DetectedFlight;
import restwars.model.flight.DetectedFlightWithSender;
import restwars.model.flight.Flight;
import restwars.model.ship.Ships;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;
import restwars.storage.scenario.impl.FlightScenario;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqDetectedFlightDAOTest extends DatabaseTest {
    private JooqDetectedFlightDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqDetectedFlightDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        DetectedFlight detectedFlight = new DetectedFlight(FlightScenario.Player1.FLIGHT_2.getId(), BasicScenario.Player1.PLAYER.getId(), 10);
        sut.insert(detectedFlight);

        List<Map<String, Object>> rows = select("SELECT * FROM detected_flight WHERE flight_id = ?", detectedFlight.getFlightId());

        assertThat(rows, hasSize(1));
        verifyRow(rows.get(0), detectedFlight);
    }

    private void verifyRow(Map<String, Object> row, DetectedFlight detectedFlight) {
        assertThat(row.get("flight_id"), is(detectedFlight.getFlightId()));
        assertThat(row.get("player_id"), is(detectedFlight.getPlayerId()));
        assertThat(row.get("approximated_fleet_size"), is(detectedFlight.getApproximatedFleetSize()));
    }

    @Test
    public void testFindWithPlayer() throws Exception {
        DetectedFlight expected = FlightScenario.Player1.DETECTED_FLIGHT;
        List<DetectedFlightWithSender> actual = sut.findWithPlayer(expected.getPlayerId());

        assertThat(actual, hasSize(1));
        // The flights has no ships, this is intended
        Flight flight = FlightScenario.Player1.FLIGHT_1.withShips(Ships.EMPTY);
        assertThat(actual.get(0), is(new DetectedFlightWithSender(expected, flight, BasicScenario.Player1.PLAYER)));
    }

    @Test
    public void testDelete() throws Exception {
        DetectedFlight detectedFlight = FlightScenario.Player1.DETECTED_FLIGHT;
        sut.delete(detectedFlight.getFlightId());

        List<Map<String, Object>> rows = select("SELECT * FROM detected_flight WHERE flight_id = ?", detectedFlight.getFlightId());

        assertThat(rows, hasSize(0));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                FlightScenario.create()
        );
    }
}