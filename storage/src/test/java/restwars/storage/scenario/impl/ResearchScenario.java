package restwars.storage.scenario.impl;

import restwars.model.technology.Research;
import restwars.model.technology.TechnologyType;
import restwars.storage.scenario.AbstractFreemarkerScenario;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResearchScenario extends AbstractFreemarkerScenario<ResearchScenario.Model> {
    public static class Player1 {
        public static class Planet1 {
            public static Research RESEARCH = new Research(
                    UUID.fromString("c5068b50-a991-11e4-bcd8-0800200c9a66"), TechnologyType.BUILDING_BUILD_COST_REDUCTION, 5,
                    1, 2, BasicScenario.Player1.Planet1.PLANET.getId(), BasicScenario.Player1.PLAYER.getId()
            );
        }

    }

    public static class Model {
        private final List<Research> researches;

        public Model(List<Research> researches) {
            this.researches = researches;
        }

        public List<Research> getResearches() {
            return researches;
        }
    }

    private static final ResearchScenario INSTANCE = new ResearchScenario();

    public static ResearchScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<Research> researches = Arrays.asList(
                Player1.Planet1.RESEARCH
        );
        return new Model(researches);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/research.sql.ftl";
    }
}
