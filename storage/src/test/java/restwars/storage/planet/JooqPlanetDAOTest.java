package restwars.storage.planet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.planet.PlanetWithOwner;
import restwars.model.resource.Resources;
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

public class JooqPlanetDAOTest extends DatabaseTest {
    private JooqPlanetDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqPlanetDAO(getUnitOfWorkService());
    }

    @Override
    protected Scenario getScenario() {
        return BasicScenario.create();
    }

    @Test
    public void testFindWithOwnerId() throws Exception {
        List<Planet> planets = sut.findWithOwnerId(BasicScenario.Player1.PLAYER.getId());

        assertThat(planets, hasSize(2));
        assertThat(planets.get(0), is(BasicScenario.Player1.Planet1.PLANET));
        assertThat(planets.get(1), is(BasicScenario.Player1.Planet2.PLANET));
    }

    @Test
    public void testFindWithLocation() throws Exception {
        Optional<Planet> planet = sut.findWithLocation(BasicScenario.Player1.Planet1.PLANET.getLocation());

        assertThat(planet.isPresent(), is(true));
        assertThat(planet.get(), is(BasicScenario.Player1.Planet1.PLANET));
    }

    @Test
    public void testFindWithLocation2() throws Exception {
        // Planet at that location doesn't exist
        Optional<Planet> planet = sut.findWithLocation(new Location(1, 1, 1));

        assertThat(planet.isPresent(), is(false));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Planet> planets = sut.findAll();

        assertThat(planets, hasSize(3));
        assertThat(planets.get(0), is(BasicScenario.Player1.Planet1.PLANET));
        assertThat(planets.get(1), is(BasicScenario.Player1.Planet2.PLANET));
        assertThat(planets.get(2), is(BasicScenario.Player2.Planet1.PLANET));
    }

    @Test
    public void testFindWithId() throws Exception {
        Optional<Planet> planet = sut.findWithId(BasicScenario.Player1.Planet1.PLANET.getId());

        assertThat(planet.isPresent(), is(true));
        assertThat(planet.get(), is(BasicScenario.Player1.Planet1.PLANET));
    }

    @Test
    public void testFindWithId2() throws Exception {
        // Planet with this id doesn't exist
        Optional<Planet> planet = sut.findWithId(UUID.fromString("b74b7d6c-846f-4000-82e5-6e9aab62f8d6"));

        assertThat(planet.isPresent(), is(false));
    }

    @Test
    public void testFindInRange() throws Exception {
        Location location = BasicScenario.Player1.Planet1.PLANET.getLocation();

        // This should find only one planet
        List<PlanetWithOwner> planets = sut.findInRange(location.getGalaxy(), location.getGalaxy(), location.getSolarSystem(), location.getSolarSystem(), location.getPlanet(), location.getPlanet());

        assertThat(planets, hasSize(1));
        assertThat(planets.get(0), is(new PlanetWithOwner(BasicScenario.Player1.Planet1.PLANET, BasicScenario.Player1.PLAYER)));
    }

    @Test
    public void testFindInRange2() throws Exception {
        Location location = BasicScenario.Player1.Planet2.PLANET.getLocation();

        // This should find only one planet
        List<PlanetWithOwner> planets = sut.findInRange(location.getGalaxy(), location.getGalaxy(), location.getSolarSystem(), location.getSolarSystem(), location.getPlanet(), location.getPlanet());

        assertThat(planets, hasSize(1));
        assertThat(planets.get(0), is(new PlanetWithOwner(BasicScenario.Player1.Planet2.PLANET, BasicScenario.Player1.PLAYER)));
    }

    @Test
    public void testFindInRange3() throws Exception {
        // This should find all planets
        List<PlanetWithOwner> planets = sut.findInRange(1, 10, 1, 10, 1, 10);

        assertThat(planets, hasSize(3));
        assertThat(planets.get(0), is(new PlanetWithOwner(BasicScenario.Player1.Planet1.PLANET, BasicScenario.Player1.PLAYER)));
        assertThat(planets.get(1), is(new PlanetWithOwner(BasicScenario.Player1.Planet2.PLANET, BasicScenario.Player1.PLAYER)));
        assertThat(planets.get(2), is(new PlanetWithOwner(BasicScenario.Player2.Planet1.PLANET, BasicScenario.Player2.PLAYER)));
    }

    private void verifyRow(Map<String, Object> row, Planet planet) {
        assertThat(row.get("id"), is(planet.getId()));
        assertThat(row.get("location_galaxy"), is(planet.getLocation().getGalaxy()));
        assertThat(row.get("location_solar_system"), is(planet.getLocation().getSolarSystem()));
        assertThat(row.get("location_planet"), is(planet.getLocation().getPlanet()));
        assertThat(row.get("owner_id"), is(planet.getOwnerId()));
        assertThat(row.get("crystals"), is(planet.getResources().getCrystals()));
        assertThat(row.get("gas"), is(planet.getResources().getGas()));
        assertThat(row.get("energy"), is(planet.getResources().getEnergy()));

    }

    @Test
    public void testInsert() throws Exception {
        Planet planet = new Planet(UUID.fromString("fd8694f8-c898-4ad4-80b6-a78780e63672"), new Location(11, 11, 11), BasicScenario.Player1.PLAYER.getId(), new Resources(1, 2, 3));
        sut.insert(planet);

        List<Map<String, Object>> resultSet = select("SELECT * FROM planet WHERE id = ?", planet.getId());

        assertThat(resultSet, hasSize(1));
        verifyRow(resultSet.get(0), planet);
    }

    @Test
    public void testUpdate() throws Exception {
        Planet planet = new Planet(BasicScenario.Player1.Planet1.PLANET.getId(), new Location(11, 11, 11), BasicScenario.Player1.PLAYER.getId(), new Resources(4, 8, 15));
        sut.update(planet);

        List<Map<String, Object>> resultSet = select("SELECT * FROM planet WHERE id = ?", planet.getId());

        assertThat(resultSet, hasSize(1));
        verifyRow(resultSet.get(0), planet);
    }
}