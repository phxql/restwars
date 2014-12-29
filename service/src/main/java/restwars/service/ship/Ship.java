package restwars.service.ship;

import com.google.common.base.Preconditions;

public class Ship {
    private final ShipType type;

    private final int amount;

    public Ship(ShipType type, int amount) {
        Preconditions.checkArgument(amount >= 0, "amount must be >= 0");

        this.type = Preconditions.checkNotNull(type, "type");
        this.amount = amount;
    }

    public ShipType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
