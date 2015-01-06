package restwars.restapi.dto.event;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "An event")
public class EventResponse {
    @ApiModelProperty(value = "Planet", required = true)
    private String location;
    @ApiModelProperty(value = "Type", required = true)
    private String type;
    @ApiModelProperty(value = "Round", required = true)
    private long round;

    public EventResponse() {
    }

    public EventResponse(String location, String type, long round) {
        this.location = location;
        this.type = type;
        this.round = round;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
                .add("location", location)
                .add("type", type)
                .add("round", round)
                .toString();
    }
}
