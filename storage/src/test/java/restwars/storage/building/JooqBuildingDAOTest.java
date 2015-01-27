package restwars.storage.building;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.building.Buildings;
import restwars.model.planet.Location;
import restwars.storage.DatabaseTest;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqBuildingDAOTest extends DatabaseTest {
    private static final UUID PLANET_ID = UUID.fromString("ab8657a1-f418-420a-a44a-ab5adeb7f4f1");
    private static final UUID PLAYER_ID = UUID.fromString("60f19086-8daa-4c02-ac41-35f7e2727d99");

    private JooqBuildingDAO sut;
    private static final Building COMMAND_CENTER = new Building(UUID.fromString("701a4465-eefb-4db7-8054-5a54c6ee182e"), BuildingType.COMMAND_CENTER, 1, PLANET_ID);
    private static final Building SHIPYARD = new Building(UUID.fromString("f7a42fbe-0abc-43d3-b78e-96ca52960d54"), BuildingType.SHIPYARD, 2, PLANET_ID);

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        prepare("player.sql", "planet.sql", "building.sql");
        sut = new JooqBuildingDAO(getUnitOfWorkService());
    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        Buildings buildings = sut.findWithPlanetId(PLANET_ID);

        assertThat(buildings, hasSize(2));
        assertThat(buildings.get(0), is(COMMAND_CENTER));
        assertThat(buildings.get(1), is(SHIPYARD));
    }

    @Test
    public void testFindWithPlanetLocationAndType() throws Exception {
        Optional<Building> building = sut.findWithPlanetLocationAndType(new Location(1, 2, 3), BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(true));
        assertThat(building.get(), is(COMMAND_CENTER));
    }

    @Test
    public void testFindWithPlanetLocationAndType2() throws Exception {
        // Crystal mine isn't on the planet
        Optional<Building> building = sut.findWithPlanetLocationAndType(new Location(1, 2, 3), BuildingType.CRYSTAL_MINE);
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
        Optional<Building> building = sut.findWithPlanetIdAndType(PLANET_ID, BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(true));
        assertThat(building.get(), is(COMMAND_CENTER));
    }

    @Test
    public void testFindWithPlanetIdAndType2() throws Exception {
        // Crystal mine isn't on the planet
        Optional<Building> building = sut.findWithPlanetIdAndType(PLANET_ID, BuildingType.CRYSTAL_MINE);
        assertThat(building.isPresent(), is(false));
    }

    @Test
    public void testFindWithPlanetIdAndType3() throws Exception {
        // Planet doesn't exist
        Optional<Building> building = sut.findWithPlanetIdAndType(UUID.fromString("a1a7c2d7-0fea-41a0-b521-6c1c42b7189e"), BuildingType.COMMAND_CENTER);
        assertThat(building.isPresent(), is(false));
    }
}