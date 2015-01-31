package restwars.storage.player;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.player.Player;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqPlayerDAOTest extends DatabaseTest {
    private JooqPlayerDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqPlayerDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        Player player = new Player(UUID.fromString("66aaab40-a72f-11e4-bcd8-0800200c9a66"), "username", "password");
        sut.insert(player);

        List<Map<String, Object>> resultSet = select("SELECT * FROM player WHERE id = ?", player.getId());

        assertThat(resultSet, hasSize(1));
        verifyRow(resultSet.get(0), player);
    }

    private void verifyRow(Map<String, Object> row, Player player) {
        assertThat(row.get("id"), is(player.getId()));
        assertThat(row.get("username"), is(player.getUsername()));
        assertThat(row.get("password"), is(player.getPassword()));
    }

    @Test
    public void testFindWithUsername() throws Exception {
        Optional<Player> player = sut.findWithUsername(BasicScenario.Player1.PLAYER.getUsername());

        assertThat(player.isPresent(), is(true));
        assertThat(player.get(), is(BasicScenario.Player1.PLAYER));
    }

    @Test
    public void testFindWithUsername2() throws Exception {
        // Player with this username doesn't exist
        Optional<Player> player = sut.findWithUsername("dummy");

        assertThat(player.isPresent(), is(false));
    }

    @Override
    protected Scenario getScenario() {
        return BasicScenario.create();
    }
}