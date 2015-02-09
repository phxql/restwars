package restwars;

import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.resource.Resources;

import java.util.UUID;

public final class Data {
    private Data() {
    }

    public static final class Player1 {
        public static final Player PLAYER = new Player(UUID.randomUUID(), "username", "password");

        public static final class Planet1 {
            public static final Planet PLANET = new Planet(UUID.randomUUID(), new Location(1, 1, 1), PLAYER.getId(), new Resources(100, 200, 300));
        }
    }
}
