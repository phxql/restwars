package restwars.storage.scenario.impl;

import restwars.model.building.BuildingType;
import restwars.model.building.ConstructionSite;
import restwars.model.resource.Resources;
import restwars.storage.scenario.AbstractFreemarkerScenario;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ConstructionSiteScenario extends AbstractFreemarkerScenario<ConstructionSiteScenario.Model> {
    public static class Player1 {
        public static class Planet1 {
            public static ConstructionSite CONSTRUCTION_SITE = new ConstructionSite(
                    UUID.fromString("9841dec0-a730-11e4-bcd8-0800200c9a66"), BuildingType.TELESCOPE, 1, BasicScenario.Player1.Planet1.PLANET.getId(),
                    BasicScenario.Player1.PLAYER.getId(), 1, 2,
                    new Resources(1, 2, 3));
        }

    }

    public static class Model {
        private final List<ConstructionSite> constructionSites;

        public Model(List<ConstructionSite> constructionSites) {
            this.constructionSites = constructionSites;
        }

        public List<ConstructionSite> getConstructionSites() {
            return constructionSites;
        }
    }

    private static final ConstructionSiteScenario INSTANCE = new ConstructionSiteScenario();

    public static ConstructionSiteScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<ConstructionSite> constructionSites = Arrays.asList(
                Player1.Planet1.CONSTRUCTION_SITE
        );

        return new Model(constructionSites);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/construction-site.sql.ftl";
    }
}
