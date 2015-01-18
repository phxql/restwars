package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

@ApiModel(description = "Ship metadata")
public class ShipMetadataResponse {
    @ApiModelProperty(value = "Ship type", required = true)
    private String type;

    @ApiModelProperty(value = "Build time in rounds", required = true)
    private long buildTime;

    @ApiModelProperty(value = "Build cost", required = true)
    private ResourcesResponse buildCost;

    @ApiModelProperty(value = "Speed", required = true)
    private int speed;

    @ApiModelProperty(value = "Attack points", required = true)
    private int attackPoints;

    @ApiModelProperty(value = "Defense points", required = true)
    private int defensePoints;

    @ApiModelProperty(value = "Storage capacity", required = true)
    private long storageCapacity;

    @ApiModelProperty(value = "Description", required = true)
    private String description;

    @ApiModelProperty(value = "Prerequisites", required = true)
    private PrerequisitesResponse prerequisites;

    public ShipMetadataResponse() {
    }

    public ShipMetadataResponse(String type, long buildTime, ResourcesResponse buildCost, int speed, int attackPoints, int defensePoints, long storageCapacity, String description, PrerequisitesResponse prerequisites) {
        this.type = type;
        this.buildTime = buildTime;
        this.buildCost = buildCost;
        this.speed = speed;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.storageCapacity = storageCapacity;
        this.description = description;
        this.prerequisites = prerequisites;
    }

    public String getType() {
        return type;
    }

    public long getBuildTime() {
        return buildTime;
    }

    public ResourcesResponse getBuildCost() {
        return buildCost;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public long getStorageCapacity() {
        return storageCapacity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBuildTime(long buildTime) {
        this.buildTime = buildTime;
    }

    public void setBuildCost(ResourcesResponse buildCost) {
        this.buildCost = buildCost;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public void setStorageCapacity(long storageCapacity) {
        this.storageCapacity = storageCapacity;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("buildTime", buildTime)
                .add("buildCost", buildCost)
                .add("speed", speed)
                .add("attackPoints", attackPoints)
                .add("defensePoints", defensePoints)
                .add("storageCapacity", storageCapacity)
                .add("description", description)
                .add("prerequisites", prerequisites)
                .toString();
    }
}
