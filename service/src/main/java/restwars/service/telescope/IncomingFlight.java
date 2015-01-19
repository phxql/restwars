package restwars.service.telescope;

import com.google.common.base.Preconditions;
import restwars.service.player.Player;
import restwars.service.ship.Flight;

public class IncomingFlight {
    private final Flight flight;

    private final Player sender;

    private final long approximatedFleetSize;

    public IncomingFlight(Flight flight, Player sender, long approximatedFleetSize) {
        Preconditions.checkArgument(approximatedFleetSize > 0, "approximatedFleetSize must be > 0");

        this.flight = Preconditions.checkNotNull(flight, "flight");
        this.sender = Preconditions.checkNotNull(sender, "sender");
        this.approximatedFleetSize = approximatedFleetSize;
    }

    public Flight getFlight() {
        return flight;
    }

    public Player getSender() {
        return sender;
    }

    public long getApproximatedFleetSize() {
        return approximatedFleetSize;
    }
}
