package restwars.storage.ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.flight.Flight;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;
import restwars.storage.scenario.impl.FlightScenario;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqFlightDAOTest extends DatabaseTest {
    private JooqFlightDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqFlightDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testFindWithPlayerId() throws Exception {
        Flight expected = FlightScenario.Player1.FLIGHT;
        List<Flight> flights = sut.findWithPlayerId(expected.getPlayerId());

        assertThat(flights, hasSize(1));
        assertThat(flights.get(0), is(expected));
    }

    @Test
    public void testFindWithArrival() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testFindWithTypeAndDetected() throws Exception {

    }

    @Test
    public void testFindWithStart() throws Exception {

    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                FlightScenario.create()
        );
    }
}