package restwars.rest.api.metadata;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.rest.api.ResourcesResponse;

@ApiModel(description = "Technology metadata")
public class TechnologyMetadataResponse {
    @ApiModelProperty(value = "Technology type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

    @ApiModelProperty(value = "Research time in rounds", required = true)
    private final long researchTime;

    @ApiModelProperty(value = "Research cost", required = true)
    private final ResourcesResponse researchCost;

    public TechnologyMetadataResponse(String type, int level, long researchTime, ResourcesResponse researchCost) {
        this.type = type;
        this.level = level;
        this.researchTime = researchTime;
        this.researchCost = researchCost;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public long getResearchTime() {
        return researchTime;
    }

    public ResourcesResponse getResearchCost() {
        return researchCost;
    }
}
