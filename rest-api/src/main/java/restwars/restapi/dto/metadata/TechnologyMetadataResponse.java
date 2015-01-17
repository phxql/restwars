package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

@ApiModel(description = "Technology metadata")
public class TechnologyMetadataResponse {
    @ApiModelProperty(value = "Technology type", required = true)
    private String type;

    @ApiModelProperty(value = "Level", required = true)
    private int level;

    @ApiModelProperty(value = "Research time in rounds", required = true)
    private long researchTime;

    @ApiModelProperty(value = "Research cost", required = true)
    private ResourcesResponse researchCost;

    @ApiModelProperty(value = "Description", required = true)
    private String description;

    public TechnologyMetadataResponse() {
    }

    public TechnologyMetadataResponse(String type, int level, long researchTime, ResourcesResponse researchCost, String description) {
        this.type = type;
        this.level = level;
        this.researchTime = researchTime;
        this.researchCost = researchCost;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setResearchTime(long researchTime) {
        this.researchTime = researchTime;
    }

    public void setResearchCost(ResourcesResponse researchCost) {
        this.researchCost = researchCost;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("level", level)
                .add("researchTime", researchTime)
                .add("researchCost", researchCost)
                .add("description", description)
                .toString();
    }
}
