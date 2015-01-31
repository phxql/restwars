package restwars.storage.technology;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.technology.Technologies;
import restwars.model.technology.Technology;
import restwars.model.technology.TechnologyType;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.BasicScenario;
import restwars.storage.scenario.Scenario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqTechnologyDAOTest extends DatabaseTest {
    private JooqTechnologyDAO sut;

    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqTechnologyDAO(getUnitOfWorkService());
    }

    @Override
    protected Scenario getScenario() {
        return BasicScenario.create();
    }

    @Test
    public void testFindAllWithPlayerId() throws Exception {
        Technologies technologies = sut.findAllWithPlayerId(BasicScenario.Player1.PLAYER.getId());

        assertThat(technologies, hasSize(2));
        assertThat(technologies.get(0), is(BasicScenario.Player1.TECHNOLOGY_1));
        assertThat(technologies.get(1), is(BasicScenario.Player1.TECHNOLOGY_2));
    }

    @Test
    public void testFindWithPlayerId() throws Exception {
        Optional<Technology> technology = sut.findWithPlayerId(BasicScenario.Player1.TECHNOLOGY_1.getPlayerId(), BasicScenario.Player1.TECHNOLOGY_1.getType());

        assertThat(technology.isPresent(), is(true));
        assertThat(technology.get(), is(BasicScenario.Player1.TECHNOLOGY_1));
    }

    @Test
    public void testUpdate() throws Exception {
        Technology updatedTechnology = new Technology(BasicScenario.Player1.TECHNOLOGY_1.getId(), TechnologyType.COMBUSTION_ENGINE, 5, BasicScenario.Player2.PLAYER.getId());

        sut.update(updatedTechnology);

        List<Map<String, Object>> rows = select("SELECT * FROM technology WHERE id = ?", updatedTechnology.getId());
        assertThat(rows, hasSize(1));
        verifyRow(rows.get(0), updatedTechnology);
    }

    @Test
    public void testInsert() throws Exception {
        Technology technology = new Technology(UUID.fromString("a8188140-a97a-11e4-bcd8-0800200c9a66"), TechnologyType.COMBUSTION_ENGINE, 10, BasicScenario.Player2.PLAYER.getId());
        sut.insert(technology);

        List<Map<String, Object>> rows = select("SELECT * FROM technology WHERE id = ?", technology.getId());

        assertThat(rows, hasSize(1));
        verifyRow(rows.get(0), technology);
    }

    private void verifyRow(Map<String, Object> row, Technology technology) {
        assertThat(row.get("id"), is(technology.getId()));
        assertThat(row.get("type"), is(technology.getType().getId()));
        assertThat(row.get("level"), is(technology.getLevel()));
        assertThat(row.get("player_id"), is(technology.getPlayerId()));
    }
}