package restwars.rest.api.player;

import com.google.common.base.Preconditions;
import restwars.rest.api.planet.PlanetDTO;

import java.util.List;

public class PlayerDTO {
    private final String username;

    private final List<PlanetDTO> planets;

    public PlayerDTO(String username, List<PlanetDTO> planets) {
        this.username = Preconditions.checkNotNull(username, "planets");
        this.planets = Preconditions.checkNotNull(planets, "planets");
    }

    public String getUsername() {
        return username;
    }

    public List<PlanetDTO> getPlanets() {
        return planets;
    }
}
