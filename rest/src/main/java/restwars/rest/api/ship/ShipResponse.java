package restwars.rest.api.ship;

import com.google.common.base.Preconditions;
import restwars.service.ship.Ship;

public class ShipResponse {
    private final String type;

    private final long count;

    public ShipResponse(String type, long count) {
        this.type = Preconditions.checkNotNull(type, "type");
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public long getCount() {
        return count;
    }

    public static ShipResponse fromShip(Ship ship) {
        Preconditions.checkNotNull(ship, "ship");

        return new ShipResponse(ship.getType().toString(), ship.getAmount());
    }
}
