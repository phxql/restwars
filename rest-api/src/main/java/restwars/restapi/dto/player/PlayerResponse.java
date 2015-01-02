package restwars.restapi.dto.player;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.planet.PlanetResponse;

import java.util.List;

@ApiModel(description = "A player")
public class PlayerResponse {
    @ApiModelProperty(value = "Username", required = true)
    private String username;

    @ApiModelProperty(value = "Owned planets", required = true)
    private List<PlanetResponse> planets;

    public PlayerResponse() {
    }

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlanets(List<PlanetResponse> planets) {
        this.planets = planets;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("planets", planets)
                .toString();
    }
}
