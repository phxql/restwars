package restwars.restapi.dto.player;

import com.google.common.base.Objects;
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

    @ApiModelProperty(value = "Points", required = true)
    private long points;

    public PlayerResponse() {
    }

    public PlayerResponse(String username, List<PlanetResponse> planets, long points) {
        this.username = username;
        this.planets = planets;
        this.points = points;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("planets", planets)
                .add("points", points)
                .toString();
    }
}
