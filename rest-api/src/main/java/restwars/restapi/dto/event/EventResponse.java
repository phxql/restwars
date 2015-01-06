package restwars.restapi.dto.event;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "An event")
public class EventResponse {
    @ApiModelProperty(value = "Planet", required = true)
    private String planetId;
    @ApiModelProperty(value = "Type", required = true)
    private String type;
    @ApiModelProperty(value = "Round", required = true)
    private long round;

    public EventResponse() {
    }

    public EventResponse(String planetId, String type, long round) {
        this.planetId = planetId;
        this.type = type;
        this.round = round;
    }

    public String getPlanetId() {
        return planetId;
    }

    public void setPlanetId(String planetId) {
        this.planetId = planetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("planetId", planetId)
                .add("type", type)
                .add("round", round)
                .toString();
    }
}
