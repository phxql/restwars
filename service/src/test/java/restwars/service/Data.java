package restwars.service;

import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.building.ConstructionSite;
import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.resource.Resources;

import java.util.UUID;

public final class Data {
    private Data() {
    }

    public static class Player1 {
        public final static Player PLAYER = new Player(UUID.fromString("68d6ded1-4e03-43c8-883c-46faa17eee48"), "player1", "player1");

        public static class Planet1 {
            public final static Planet PLANET = new Planet(UUID.fromString("63cbb2f1-ceb1-4347-b8e0-4b11bbc5d44e"), new Location(1, 2, 3), PLAYER.getId(), new Resources(10, 20, 30));

            public final static Building COMMAND_CENTER = new Building(UUID.fromString("20e91b69-ae0f-4054-9a9f-0076de37b1d9"), BuildingType.COMMAND_CENTER, 1, PLANET.getId());

            public final static ConstructionSite CONSTRUCTION_SITE = new ConstructionSite(UUID.fromString("854017e3-872c-4caf-a2d9-26e42a623344"), BuildingType.SHIPYARD, 1, PLANET.getId(), PLAYER.getId(), 1, 2);
        }
    }
}
