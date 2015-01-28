package restwars.storage.building;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.building.Buildings;
import restwars.model.planet.Location;
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

public class JooqBuildingDAOTest extends DatabaseTest {
    private JooqBuildingDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqBuildingDAO(getUnitOfWorkService());
    }

    @Override
    protected Scenario getScenario() {
        return BasicScenario.create();
    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        Buildings buildings = sut.findWithPlanetId(BasicScenario.Player1.Planet1.PLANET.getId());

        assertThat(buildings, hasSize(2));
        assertThat(buildings.get(0), is(BasicScenario.Player1.Planet1.COMMAND_CENTER));
        assertThat(buildings.get(1), is(BasicScenario.Player1.Planet1.SHIPYARD));
    }

    @Test
    public void testFindWithPlanetLocationAndType() throws Exception {
        Optional<Building> building = sut.findWithPlanetLocationAndType(BasicScenario.Player1.Planet1.PLANET.getLocation(), BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(true));
        assertThat(building.get(), is(BasicScenario.Player1.Planet1.COMMAND_CENTER));
    }

    @Test
    public void testFindWithPlanetLocationAndType2() throws Exception {
        // Crystal mine isn't on the planet
        Optional<Building> building = sut.findWithPlanetLocationAndType(BasicScenario.Player1.Planet1.PLANET.getLocation(), BuildingType.CRYSTAL_MINE);
        assertThat(building.isPresent(), is(false));
    }

    @Test
    public void testFindWithPlanetLocationAndType3() throws Exception {
        // Planet doesn't exist
        Optional<Building> building = sut.findWithPlanetLocationAndType(new Location(2, 3, 4), BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(false));
    }

    @Test
    public void testFindWithPlanetIdAndType() throws Exception {
        Optional<Building> building = sut.findWithPlanetIdAndType(BasicScenario.Player1.Planet1.PLANET.getId(), BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(true));
        assertThat(building.get(), is(BasicScenario.Player1.Planet1.COMMAND_CENTER));
    }

    @Test
    public void testFindWithPlanetIdAndType2() throws Exception {
        // Crystal mine isn't on the planet
        Optional<Building> building = sut.findWithPlanetIdAndType(BasicScenario.Player1.Planet1.PLANET.getId(), BuildingType.CRYSTAL_MINE);
        assertThat(building.isPresent(), is(false));
    }

    @Test
    public void testFindWithPlanetIdAndType3() throws Exception {
        // Planet doesn't exist
        Optional<Building> building = sut.findWithPlanetIdAndType(UUID.fromString("a1a7c2d7-0fea-41a0-b521-6c1c42b7189e"), BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(false));
    }

    private void verifyRow(Map<String, Object> row, Building building) {
        assertThat(row.get("id"), is(building.getId()));
        assertThat(row.get("type"), is(building.getType().getId()));
        assertThat(row.get("level"), is(building.getLevel()));
        assertThat(row.get("planet_id"), is(building.getPlanetId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Building building = BasicScenario.Player1.Planet1.COMMAND_CENTER;
        Building updatedBuilding = new Building(building.getId(), BuildingType.RESEARCH_CENTER, 2, BasicScenario.Player1.Planet1.COMMAND_CENTER.getPlanetId());

        sut.update(updatedBuilding);

        List<Map<String, Object>> resultSet = select("SELECT * FROM building WHERE id = ?", building.getId());

        assertThat(resultSet, hasSize(1));
        verifyRow(resultSet.get(0), updatedBuilding);
    }

    @Test
    public void testInsert() throws Exception {
        UUID id = UUID.fromString("2e43f380-a6c9-11e4-bcd8-0800200c9a66");

        Building building = new Building(id, BuildingType.GAS_REFINERY, 1, BasicScenario.Player1.Planet1.PLANET.getId());
        sut.insert(building);

        List<Map<String, Object>> resultSet = select("SELECT * FROM building WHERE id = ?", id);

        assertThat(resultSet, hasSize(1));
        verifyRow(resultSet.get(0), building);
    }
}