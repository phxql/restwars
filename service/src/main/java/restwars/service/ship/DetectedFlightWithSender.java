package restwars.service.ship;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.service.player.Player;

public class DetectedFlightWithSender {
    private final DetectedFlight detectedFlight;

    private final Flight flight;

    private final Player sender;

    public DetectedFlightWithSender(DetectedFlight detectedFlight, Flight flight, Player sender) {
        this.detectedFlight = Preconditions.checkNotNull(detectedFlight, "detectedFlight");
        this.flight = Preconditions.checkNotNull(flight, "flight");
        this.sender = Preconditions.checkNotNull(sender, "sender");
    }

    public DetectedFlight getDetectedFlight() {
        return detectedFlight;
    }

    public Flight getFlight() {
        return flight;
    }

    public Player getSender() {
        return sender;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("detectedFlight", detectedFlight)
                .add("flight", flight)
                .add("sender", sender)
                .toString();
    }
}
