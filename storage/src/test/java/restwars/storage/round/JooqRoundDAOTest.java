package restwars.storage.round;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqRoundDAOTest extends DatabaseTest {
    private JooqRoundDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqRoundDAO(getUnitOfWorkService());
    }

    @Test
    public void testGetRound() throws Exception {
        long round = sut.getRound();

        assertThat(round, is(10L));
    }

    @Test
    public void testUpdateRound() throws Exception {
        sut.updateRound(20);

        List<Map<String, Object>> rows = select("SELECT * from round");

        assertThat(rows, hasSize(1));
        assertThat(rows.get(0).get("current_round"), is(20L));
    }

    @Override
    protected Scenario getScenario() {
        return BasicScenario.create();
    }
}