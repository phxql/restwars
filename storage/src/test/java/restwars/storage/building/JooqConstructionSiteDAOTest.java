package restwars.storage.building;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.service.building.ConstructionSite;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.BasicScenario;
import restwars.storage.scenario.ConstructionSiteScenario;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;

import java.util.List;

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

    }

    @Test
    public void testFindWithPlanetId() throws Exception {
        List<ConstructionSite> constructionSites = sut.findWithPlanetId(BasicScenario.Player1.Planet1.PLANET.getId());

        assertThat(constructionSites, hasSize(1));
        assertThat(constructionSites.get(0), is(ConstructionSiteScenario.Player1.Planet1.CONSTRUCTION_SITE));
    }

    @Test
    public void testFindWithDone() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(), ConstructionSiteScenario.create()
        );
    }
}