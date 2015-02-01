package restwars.storage.ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.ship.ShipInConstruction;
import restwars.model.ship.ShipType;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;
import restwars.storage.scenario.impl.ShipInConstructionScenario;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqShipInConstructionDAOTest extends DatabaseTest {
    private JooqShipInConstructionDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqShipInConstructionDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        ShipInConstruction shipInConstruction = new ShipInConstruction(UUID.fromString("3c67aef2-9ff0-470e-acda-b26bc0a5fa4c"),
                ShipType.COLONY, BasicScenario.Player1.Planet1.PLANET.getId(), BasicScenario.Player1.PLAYER.getId(), 1, 2);

        sut.insert(shipInConstruction);

        List<Map<String, Object>> rows = select("SELECT * FROM ship_in_construction WHERE id = ?", shipInConstruction.getId());

        assertThat(rows, hasSize(1));
        verifyRow(rows.get(0), shipInConstruction);
    }

    private void verifyRow(Map<String, Object> row, ShipInConstruction shipInConstruction) {
        assertThat(row.get("id"), is(shipInConstruction.getId()));
        assertThat(row.get("type"), is(shipInConstruction.getType().getId()));
        assertThat(row.get("done"), is(shipInConstruction.getDone()));
        assertThat(row.get("planet_id"), is(shipInConstruction.getPlanetId()));
        assertThat(row.get("player_id"), is(shipInConstruction.getPlayerId()));
        assertThat(row.get("started"), is(shipInConstruction.getStarted()));
    }

    @Test
    public void testFindWithDone() throws Exception {
        ShipInConstruction expected = ShipInConstructionScenario.Player1.Planet1.SHIP_IN_CONSTRUCTION;
        List<ShipInConstruction> shipsInConstruction = sut.findWithDone(expected.getDone());

        assertThat(shipsInConstruction, hasSize(1));
        assertThat(shipsInConstruction.get(0), is(expected));
    }

    @Test
    public void testFindWithDone2() throws Exception {
        List<ShipInConstruction> shipsInConstruction = sut.findWithDone(100);

        assertThat(shipsInConstruction, hasSize(0));
    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        ShipInConstruction expected = ShipInConstructionScenario.Player1.Planet1.SHIP_IN_CONSTRUCTION;
        List<ShipInConstruction> shipsInConstruction = sut.findWithPlanetId(expected.getPlanetId());

        assertThat(shipsInConstruction, hasSize(1));
        assertThat(shipsInConstruction.get(0), is(expected));
    }

    @Test
    public void testFindWithPlanetId2() throws Exception {
        List<ShipInConstruction> shipsInConstruction = sut.findWithPlanetId(BasicScenario.Player2.Planet1.PLANET.getId());

        assertThat(shipsInConstruction, hasSize(0));
    }

    @Test
    public void testDelete() throws Exception {
        ShipInConstruction toDelete = ShipInConstructionScenario.Player1.Planet1.SHIP_IN_CONSTRUCTION;
        sut.delete(toDelete);

        List<Map<String, Object>> rows = select("SELECT * FROM ship_in_construction WHERE id = ?", toDelete.getId());
        assertThat(rows, hasSize(0));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                ShipInConstructionScenario.create()
        );
    }
}