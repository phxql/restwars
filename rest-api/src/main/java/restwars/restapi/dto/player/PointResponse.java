package restwars.restapi.dto.player;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Point")
public class PointResponse {
    @ApiModelProperty(value = "Points", required = true)
    private long points;

    @ApiModelProperty(value = "Round", required = true)
    private long round;

    public PointResponse() {
    }

    public PointResponse(long points, long round) {
        this.points = points;
        this.round = round;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointResponse that = (PointResponse) o;

        return Objects.equal(this.points, that.points) &&
                Objects.equal(this.round, that.round);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(points, round);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("points", points)
                .add("round", round)
                .toString();
    }
}
