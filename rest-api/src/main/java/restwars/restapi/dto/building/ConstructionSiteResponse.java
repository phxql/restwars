package restwars.restapi.dto.building;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Construction site")
public class ConstructionSiteResponse {
    @ApiModelProperty(value = "Building type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

    @ApiModelProperty(value = "Round started", required = true)
    private final long started;

    @ApiModelProperty(value = "Round done", required = true)
    private final long done;

    public ConstructionSiteResponse(String type, int level, long started, long done) {
        this.type = type;
        this.level = level;
        this.started = started;
        this.done = done;
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
}
