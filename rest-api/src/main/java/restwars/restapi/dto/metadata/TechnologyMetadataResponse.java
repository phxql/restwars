package restwars.restapi.dto.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

import javax.annotation.Nullable;

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

    @ApiModelProperty(value = "Prerequisites", required = true)
    private PrerequisitesResponse prerequisites;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Build cost reduction in percent", required = false)
    private Double buildCostReduction;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Flight cost reduction in percent", required = false)
    private Double flightCostReduction;

    public TechnologyMetadataResponse() {
    }

    public TechnologyMetadataResponse(String type, int level, long researchTime, ResourcesResponse researchCost, String description, PrerequisitesResponse prerequisites, Double buildCostReduction, Double flightCostReduction) {
        this.type = type;
        this.level = level;
        this.researchTime = researchTime;
        this.researchCost = researchCost;
        this.description = description;
        this.prerequisites = prerequisites;
        this.buildCostReduction = buildCostReduction;
        this.flightCostReduction = flightCostReduction;
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

    public PrerequisitesResponse getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(PrerequisitesResponse prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Nullable
    public Double getFlightCostReduction() {
        return flightCostReduction;
    }

    public void setFlightCostReduction(@Nullable Double flightCostReduction) {
        this.flightCostReduction = flightCostReduction;
    }

    @Nullable
    public Double getBuildCostReduction() {
        return buildCostReduction;
    }

    public void setBuildCostReduction(@Nullable Double buildCostReduction) {
        this.buildCostReduction = buildCostReduction;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("level", level)
                .add("researchTime", researchTime)
                .add("researchCost", researchCost)
                .add("description", description)
                .add("prerequisites", prerequisites)
                .add("buildCostReduction", buildCostReduction)
                .add("flightCostReduction", flightCostReduction)
                .toString();
    }
}
