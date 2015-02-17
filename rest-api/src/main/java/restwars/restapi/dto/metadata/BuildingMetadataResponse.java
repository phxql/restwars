package restwars.restapi.dto.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

import javax.annotation.Nullable;

@ApiModel(description = "Building metadata")
public class BuildingMetadataResponse {
    @ApiModelProperty(value = "Building type", required = true)
    private String type;

    @ApiModelProperty(value = "Level", required = true)
    private int level;

    @ApiModelProperty(value = "Build time in rounds", required = true)
    private long buildTime;

    @ApiModelProperty(value = "Build cost", required = true)
    private ResourcesResponse buildCost;

    @ApiModelProperty(value = "Description", required = true)
    private String description;

    @ApiModelProperty(value = "Prerequisites", required = true)
    private PrerequisitesResponse prerequisites;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Resources gathered per round", required = false)
    private ResourcesResponse resourcesPerRound;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Building build time speedup in percent", required = false)
    private Double buildingBuildTimeSpeedUp;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Research time speedup in percent", required = false)
    private Double researchTimeSpeedUp;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Ship build time speedup in percent", required = false)
    private Double shipBuildTimeSpeedUp;

    public BuildingMetadataResponse() {
    }

    public BuildingMetadataResponse(String type, int level, long buildTime, ResourcesResponse buildCost, String description, PrerequisitesResponse prerequisites, ResourcesResponse resourcesPerRound, Double buildingBuildTimeSpeedUp, Double researchTimeSpeedUp, Double shipBuildTimeSpeedUp) {
        this.type = type;
        this.level = level;
        this.buildTime = buildTime;
        this.buildCost = buildCost;
        this.description = description;
        this.prerequisites = prerequisites;
        this.resourcesPerRound = resourcesPerRound;
        this.buildingBuildTimeSpeedUp = buildingBuildTimeSpeedUp;
        this.researchTimeSpeedUp = researchTimeSpeedUp;
        this.shipBuildTimeSpeedUp = shipBuildTimeSpeedUp;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBuildTime(long buildTime) {
        this.buildTime = buildTime;
    }

    public void setBuildCost(ResourcesResponse buildCost) {
        this.buildCost = buildCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrerequisitesResponse getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(PrerequisitesResponse prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Nullable
    public ResourcesResponse getResourcesPerRound() {
        return resourcesPerRound;
    }

    public void setResourcesPerRound(@Nullable ResourcesResponse resourcesPerRound) {
        this.resourcesPerRound = resourcesPerRound;
    }

    @Nullable
    public Double getBuildingBuildTimeSpeedUp() {
        return buildingBuildTimeSpeedUp;
    }

    public void setBuildingBuildTimeSpeedUp(@Nullable Double buildingBuildTimeSpeedUp) {
        this.buildingBuildTimeSpeedUp = buildingBuildTimeSpeedUp;
    }

    @Nullable
    public Double getResearchTimeSpeedUp() {
        return researchTimeSpeedUp;
    }

    public void setResearchTimeSpeedUp(@Nullable Double researchTimeSpeedUp) {
        this.researchTimeSpeedUp = researchTimeSpeedUp;
    }

    @Nullable
    public Double getShipBuildTimeSpeedUp() {
        return shipBuildTimeSpeedUp;
    }

    public void setShipBuildTimeSpeedUp(@Nullable Double shipBuildTimeSpeedUp) {
        this.shipBuildTimeSpeedUp = shipBuildTimeSpeedUp;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("level", level)
                .add("buildTime", buildTime)
                .add("buildCost", buildCost)
                .add("description", description)
                .add("prerequisites", prerequisites)
                .add("resourcesPerRound", resourcesPerRound)
                .add("buildingBuildTimeSpeedUp", buildingBuildTimeSpeedUp)
                .add("researchTimeSpeedUp", researchTimeSpeedUp)
                .add("shipBuildTimeSpeedUp", shipBuildTimeSpeedUp)
                .toString();
    }
}
