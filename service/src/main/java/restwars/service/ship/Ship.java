package restwars.service.ship;

import com.google.common.base.Preconditions;

public class Ship {
    private final ShipType type;

    private final long count;

    public Ship(ShipType type, long count) {
        Preconditions.checkArgument(count >= 0, "count must be >= 0");

        this.type = Preconditions.checkNotNull(type, "type");
        this.count = count;
    }

    public ShipType getType() {
        return type;
    }

    public long getCount() {
        return count;
    }
}
