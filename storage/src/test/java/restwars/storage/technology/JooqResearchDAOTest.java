package restwars.storage.technology;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.technology.Research;
import restwars.model.technology.TechnologyType;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.BasicScenario;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.ResearchScenario;
import restwars.storage.scenario.Scenario;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqResearchDAOTest extends DatabaseTest {
    private JooqResearchDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqResearchDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        Research research = new Research(UUID.fromString("8fe81af0-a992-11e4-bcd8-0800200c9a66"), TechnologyType.COMBUSTION_ENGINE, 1, 1, 2,
                BasicScenario.Player1.Planet2.PLANET.getId(), BasicScenario.Player1.PLAYER.getId()
        );

        sut.insert(research);

        List<Map<String, Object>> rows = select("SELECT * FROM research WHERE id = ?", research.getId());
        assertThat(rows, hasSize(1));
        verifyRow(rows.get(0), research);
    }

    private void verifyRow(Map<String, Object> row, Research research) {
        assertThat(row.get("id"), is(research.getId()));
        assertThat(row.get("done"), is(research.getDone()));
        assertThat(row.get("level"), is(research.getLevel()));
        assertThat(row.get("type"), is(research.getType().getId()));
        assertThat(row.get("planet_id"), is(research.getPlanetId()));
        assertThat(row.get("player_id"), is(research.getPlayerId()));
        assertThat(row.get("started"), is(research.getStarted()));
    }

    @Test
    public void testFindWithDone() throws Exception {
        Research research = ResearchScenario.Player1.Planet1.RESEARCH;

        List<Research> researches = sut.findWithDone(research.getDone());

        assertThat(researches, hasSize(1));
        assertThat(researches.get(0), is(research));
    }

    @Test
    public void testFindWithDone2() throws Exception {
        List<Research> researches = sut.findWithDone(1000);

        assertThat(researches, hasSize(0));
    }

    @Test
    public void testDelete() throws Exception {
        Research research = ResearchScenario.Player1.Planet1.RESEARCH;
        sut.delete(research);

        List<Map<String, Object>> rows = select("SELECT * FROM research WHERE id = ?", research.getId());

        assertThat(rows, hasSize(0));
    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        Research research = ResearchScenario.Player1.Planet1.RESEARCH;

        List<Research> researches = sut.findWithPlanetId(research.getPlanetId());

        assertThat(researches, hasSize(1));
        assertThat(researches.get(0), is(research));
    }

    @Test
    public void testFindWithPlayerAndType() throws Exception {
        Research research = ResearchScenario.Player1.Planet1.RESEARCH;

        List<Research> researches = sut.findWithPlayerAndType(research.getPlayerId(), research.getType());

        assertThat(researches, hasSize(1));
        assertThat(researches.get(0), is(research));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                ResearchScenario.create()
        );
    }
}