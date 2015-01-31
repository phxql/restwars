package restwars.storage.building;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.building.BuildingType;
import restwars.model.building.ConstructionSite;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.BasicScenario;
import restwars.storage.scenario.ConstructionSiteScenario;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqConstructionSiteDAOTest extends DatabaseTest {
    private JooqConstructionSiteDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqConstructionSiteDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        ConstructionSite constructionSite = new ConstructionSite(UUID.fromString("aaafe2ad-fa33-464f-a6c4-57fab40d5d8a"), BuildingType.SOLAR_PANELS, 2,
                BasicScenario.Player1.Planet1.PLANET.getId(), BasicScenario.Player1.PLAYER.getId(), 1, 2);
        sut.insert(constructionSite);

        List<Map<String, Object>> result = select("SELECT * FROM construction_site WHERE id = ?", constructionSite.getId());

        assertThat(result, hasSize(1));
        verifyRow(result.get(0), constructionSite);
    }

    private void verifyRow(Map<String, Object> row, ConstructionSite constructionSite) {
        assertThat(row.get("id"), is(constructionSite.getId()));
        assertThat(row.get("type"), is(constructionSite.getType().getId()));
        assertThat(row.get("done"), is(constructionSite.getDone()));
        assertThat(row.get("started"), is(constructionSite.getStarted()));
        assertThat(row.get("level"), is(constructionSite.getLevel()));
        assertThat(row.get("planet_id"), is(constructionSite.getPlanetId()));
        assertThat(row.get("player_id"), is(constructionSite.getPlayerId()));
    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        List<ConstructionSite> constructionSites = sut.findWithPlanetId(BasicScenario.Player1.Planet1.PLANET.getId());

        assertThat(constructionSites, hasSize(1));
        assertThat(constructionSites.get(0), is(ConstructionSiteScenario.Player1.Planet1.CONSTRUCTION_SITE));
    }

    @Test
    public void testFindWithDone() throws Exception {
        List<ConstructionSite> constructionSites = sut.findWithDone(ConstructionSiteScenario.Player1.Planet1.CONSTRUCTION_SITE.getDone());

        assertThat(constructionSites, hasSize(1));
        assertThat(constructionSites.get(0), is(ConstructionSiteScenario.Player1.Planet1.CONSTRUCTION_SITE));
    }

    @Test
    public void testDelete() throws Exception {
        sut.delete(ConstructionSiteScenario.Player1.Planet1.CONSTRUCTION_SITE);

        List<Map<String, Object>> result = select("SELECT * FROM construction_site WHERE id = ?", ConstructionSiteScenario.Player1.Planet1.CONSTRUCTION_SITE.getId());

        assertThat(result, hasSize(0));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(), ConstructionSiteScenario.create()
        );
    }
}