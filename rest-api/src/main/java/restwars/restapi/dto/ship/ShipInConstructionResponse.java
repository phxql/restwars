package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

@ApiModel(description = "Ship in construction")
public class ShipInConstructionResponse {
    @ApiModelProperty(value = "Ship type", required = true)
    private String type;

    @ApiModelProperty(value = "Round started", required = true)
    private long started;

    @ApiModelProperty(value = "Round done", required = true)
    private long done;

    @ApiModelProperty(value = "Build cost", required = true)
    private ResourcesResponse buildCost;

    public ShipInConstructionResponse() {
    }

    public ShipInConstructionResponse(String type, long started, long done, ResourcesResponse buildCost) {
        this.type = type;
        this.started = started;
        this.done = done;
        this.buildCost = buildCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public long getDone() {
        return done;
    }

    public void setDone(long done) {
        this.done = done;
    }

    public ResourcesResponse getBuildCost() {
        return buildCost;
    }

    public void setBuildCost(ResourcesResponse buildCost) {
        this.buildCost = buildCost;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("started", started)
                .add("done", done)
                .add("buildCost", buildCost)
                .toString();
    }
}
