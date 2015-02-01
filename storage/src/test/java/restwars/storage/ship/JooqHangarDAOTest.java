package restwars.storage.ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.ship.Hangar;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
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

public class JooqHangarDAOTest extends DatabaseTest {
    private JooqHangarDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqHangarDAO(getUnitOfWorkService());
    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        Hangar expected = BasicScenario.Player1.Planet1.HANGAR;
        Optional<Hangar> hangar = sut.findWithPlanetId(expected.getPlanetId());

        assertThat(hangar.isPresent(), is(true));
        assertThat(hangar.get(), is(expected));
    }

    @Test
    public void testFindWithPlanetId2() throws Exception {
        // Hangar with this planet id doesn't exist
        Optional<Hangar> hangar = sut.findWithPlanetId(UUID.randomUUID());

        assertThat(hangar.isPresent(), is(false));
    }

    @Test
    public void testUpdate() throws Exception {
        Hangar hangar = BasicScenario.Player1.Planet1.HANGAR;
        Hangar updatedHangar = new Hangar(hangar.getId(), hangar.getPlanetId(), hangar.getPlayerId(), Ships.EMPTY);

        sut.update(updatedHangar);

        List<Map<String, Object>> hangarRows = select("SELECT * FROM hangar WHERE id = ?", updatedHangar.getId());
        assertThat(hangarRows, hasSize(1));
        verifyHangarRow(hangarRows.get(0), updatedHangar);

        List<Map<String, Object>> hangarShipsRows = select("SELECT * FROM hangar_ships WHERE hangar_id = ?", updatedHangar.getId());
        assertThat(hangarShipsRows, hasSize(0));
    }

    @Test
    public void testInsert() throws Exception {
        Hangar hangar = new Hangar(UUID.fromString("3e757b40-a997-11e4-bcd8-0800200c9a66"), BasicScenario.Player1.Planet2.PLANET.getId(), BasicScenario.Player1.PLAYER.getId(), new Ships(new Ship(ShipType.DAEDALUS, 7), new Ship(ShipType.PROBE, 2)));

        sut.insert(hangar);

        List<Map<String, Object>> hangarRows = select("SELECT * FROM hangar WHERE id = ?", hangar.getId());
        assertThat(hangarRows, hasSize(1));
        verifyHangarRow(hangarRows.get(0), hangar);

        List<Map<String, Object>> hangarShipsRows = select("SELECT * FROM hangar_ships WHERE hangar_id = ?", hangar.getId());
        assertThat(hangarShipsRows, hasSize(2));
        verifyHangarShipsRow(hangarShipsRows.get(0), hangar.getShips().asList().get(0));
        verifyHangarShipsRow(hangarShipsRows.get(1), hangar.getShips().asList().get(1));
    }

    private void verifyHangarShipsRow(Map<String, Object> row, Ship ship) {
        assertThat(row.get("type"), is(ship.getType().getId()));
        assertThat(row.get("amount"), is(ship.getAmount()));
    }

    private void verifyHangarRow(Map<String, Object> row, Hangar hangar) {
        assertThat(row.get("id"), is(hangar.getId()));
        assertThat(row.get("planet_id"), is(hangar.getPlanetId()));
        assertThat(row.get("player_id"), is(hangar.getPlayerId()));
    }

    @Override
    protected Scenario getScenario() {
        return BasicScenario.create();
    }
}