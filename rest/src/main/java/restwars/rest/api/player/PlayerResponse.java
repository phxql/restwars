package restwars.rest.api.player;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.rest.api.planet.PlanetResponse;

import java.util.List;

@ApiModel(description = "A player")
public class PlayerResponse {
    @ApiModelProperty(value = "Username", required = true)
    private final String username;

    @ApiModelProperty(value = "Owned planets", required = true)
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
