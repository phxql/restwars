package restwars.restapi.dto.player;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Player ranking entry")
public class PlayerRankingEntryResponse {
    @ApiModelProperty(value = "Username of the player", required = true)
    private String username;

    @ApiModelProperty(value = "Points", required = true)
    private long points;

    public PlayerRankingEntryResponse() {
    }

    public PlayerRankingEntryResponse(String username, long points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public long getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerRankingEntryResponse that = (PlayerRankingEntryResponse) o;

        return Objects.equal(this.username, that.username) &&
                Objects.equal(this.points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, points);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("points", points)
                .toString();
    }
}
