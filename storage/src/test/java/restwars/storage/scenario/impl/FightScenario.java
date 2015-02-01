package restwars.storage.scenario.impl;

import restwars.model.fight.Fight;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.storage.scenario.AbstractFreemarkerScenario;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FightScenario extends AbstractFreemarkerScenario<FightScenario.Model> {
    public static class Player1 {
        public static final Fight FIGHT = new Fight(
                UUID.fromString("0619c505-8d96-4500-bc97-35f0681a20f0"), BasicScenario.Player1.PLAYER.getId(), BasicScenario.Player2.PLAYER.getId(),
                BasicScenario.Player2.Planet1.PLANET.getId(), new Ships(new Ship(ShipType.MOSQUITO, 5), new Ship(ShipType.MULE, 1)),
                new Ships(new Ship(ShipType.DAEDALUS, 1)), new Ships(new Ship(ShipType.MOSQUITO, 1)), new Ships(new Ship(ShipType.DAEDALUS, 1)),
                10, new Resources(10, 20, 0)
        );
    }

    public static class Model {
        private final List<Fight> fights;

        public Model(List<Fight> fights) {
            this.fights = fights;
        }

        public List<Fight> getFights() {
            return fights;
        }
    }

    private static final FightScenario INSTANCE = new FightScenario();

    public static FightScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<Fight> fights = Arrays.asList(
                Player1.FIGHT
        );

        return new Model(fights);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/fight.sql.ftl";
    }
}
