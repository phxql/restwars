package restwars.storage.scenario.impl;

import restwars.model.ship.ShipInConstruction;
import restwars.model.ship.ShipType;
import restwars.storage.scenario.AbstractFreemarkerScenario;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ShipInConstructionScenario extends AbstractFreemarkerScenario<ShipInConstructionScenario.Model> {
    public static class Player1 {
        public static class Planet1 {
            public final static ShipInConstruction SHIP_IN_CONSTRUCTION = new ShipInConstruction(UUID.fromString("3ef77dd7-3a4c-425e-9789-35cc7837c12f"),
                    ShipType.MOSQUITO, BasicScenario.Player1.Planet1.PLANET.getId(), BasicScenario.Player1.PLAYER.getId(), 1, 2);
        }
    }

    public static class Model {
        private final List<ShipInConstruction> shipsInConstruction;

        public Model(List<ShipInConstruction> shipsInConstruction) {
            this.shipsInConstruction = shipsInConstruction;
        }

        public List<ShipInConstruction> getShipsInConstruction() {
            return shipsInConstruction;
        }
    }

    private static final ShipInConstructionScenario INSTANCE = new ShipInConstructionScenario();

    public static ShipInConstructionScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<ShipInConstruction> shipsInConstruction = Arrays.asList(
                Player1.Planet1.SHIP_IN_CONSTRUCTION
        );

        return new Model(shipsInConstruction);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/ship-in-construction.sql.ftl";
    }
}
