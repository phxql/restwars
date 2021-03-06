package restwars.restapi.dto.technology;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

@ApiModel(description = "Research")
public class ResearchResponse {
    @ApiModelProperty(value = "Technology type", required = true)
    private String type;

    @ApiModelProperty(value = "Level", required = true)
    private int level;

    @ApiModelProperty(value = "Round started", required = true)
    private long started;

    @ApiModelProperty(value = "Round done", required = true)
    private long done;

    @ApiModelProperty(value = "Build cost", required = true)
    private ResourcesResponse researchCost;

    public ResearchResponse() {
    }

    public ResearchResponse(String type, int level, long started, long done, ResourcesResponse researchCost) {
        this.type = type;
        this.level = level;
        this.started = started;
        this.done = done;
        this.researchCost = researchCost;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public long getStarted() {
        return started;
    }

    public long getDone() {
        return done;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public void setDone(long done) {
        this.done = done;
    }

    public ResourcesResponse getResearchCost() {
        return researchCost;
    }

    public void setResearchCost(ResourcesResponse researchCost) {
        this.researchCost = researchCost;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("level", level)
                .add("started", started)
                .add("done", done)
                .add("researchCost", researchCost)
                .toString();
    }
}
