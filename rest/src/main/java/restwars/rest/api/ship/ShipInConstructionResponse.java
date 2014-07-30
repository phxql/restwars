package restwars.rest.api.ship;

import com.google.common.base.Preconditions;
import restwars.service.ship.ShipInConstruction;

public class ShipInConstructionResponse {
    private final String type;

    private final long started;

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
