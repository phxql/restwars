package restwars.rest.api.ship;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.service.ship.ShipInConstruction;

@ApiModel(description = "Ship in construction")
public class ShipInConstructionResponse {
    @ApiModelProperty(value = "Ship type", required = true)
    private final String type;

    @ApiModelProperty(value = "Round started", required = true)
    private final long started;

    @ApiModelProperty(value = "Round done", required = true)
    private final long done;

    public ShipInConstructionResponse(String type, long started, long done) {
        this.type = Preconditions.checkNotNull(type, "type");
        this.started = started;
        this.done = done;
    }

    public String getType() {
        return type;
    }

    public long getStarted() {
        return started;
    }

    public long getDone() {
        return done;
    }

    public static ShipInConstructionResponse fromShipInConstruction(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        return new ShipInConstructionResponse(shipInConstruction.getType().toString(), shipInConstruction.getStarted(), shipInConstruction.getDone());
    }
}
