package restwars.rest.api.player;

import com.google.common.base.Preconditions;
import restwars.rest.api.planet.PlanetResponse;

import java.util.List;

public class PlayerResponse {
    private final String username;

    private final List<PlanetResponse> planets;

    public PlayerResponse(String username, List<PlanetResponse> planets) {
        this.username = Preconditions.checkNotNull(username, "planets");
        this.planets = Preconditions.checkNotNull(planets, "planets");
    }

    public String getUsername() {
        return username;
    }

    public List<PlanetResponse> getPlanets() {
        return planets;
    }
}
