package restwars.rest.api.metadata;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.rest.api.ResourcesResponse;
import restwars.service.ship.ShipType;

@ApiModel(description = "Ship metadata")
public class ShipMetadataResponse {
    @ApiModelProperty(value = "Ship type", required = true)
    private final String type;

    @ApiModelProperty(value = "Build time in rounds", required = true)
    private final long buildTime;

    @ApiModelProperty(value = "Build cost", required = true)
    private final ResourcesResponse buildCost;

    @ApiModelProperty(value = "Speed", required = true)
    private final int speed;

    @ApiModelProperty(value = "Attack points", required = true)
    private final int attackPoints;

    @ApiModelProperty(value = "Defense points", required = true)
    private final int defensePoints;

    @ApiModelProperty(value = "Storage capacity", required = true)
    private final long storageCapacity;

    public ShipMetadataResponse(String type, long buildTime, ResourcesResponse buildCost, int speed, int attackPoints, int defensePoints, long storageCapacity) {
        this.type = type;
        this.buildTime = buildTime;
        this.buildCost = buildCost;
        this.speed = speed;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.storageCapacity = storageCapacity;
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

    public static ShipMetadataResponse fromShipType(ShipType shipType) {
        Preconditions.checkNotNull(shipType, "shipType");

        return new ShipMetadataResponse(
                shipType.name(), shipType.getBuildTime(), ResourcesResponse.fromResources(shipType.getBuildCost()),
                shipType.getSpeed(), shipType.getAttackPoints(), shipType.getDefensePoints(), shipType.getStorageCapacity()
        );
    }
}
