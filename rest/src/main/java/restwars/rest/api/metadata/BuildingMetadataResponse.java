package restwars.rest.api.metadata;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.rest.api.ResourcesResponse;

@ApiModel(description = "Building metadata")
public class BuildingMetadataResponse {
    @ApiModelProperty(value = "Building type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

    @ApiModelProperty(value = "Build time in rounds", required = true)
    private final long buildTime;

    @ApiModelProperty(value = "Build cost", required = true)
    private final ResourcesResponse buildCost;

    public BuildingMetadataResponse(String type, int level, long buildTime, ResourcesResponse buildCost) {
        this.type = type;
        this.level = level;
        this.buildTime = buildTime;
        this.buildCost = buildCost;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public long getBuildTime() {
        return buildTime;
    }

    public ResourcesResponse getBuildCost() {
        return buildCost;
    }
}
