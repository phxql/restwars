package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Detected flight")
public class DetectedFlightResponse {
    @ApiModelProperty(value = "Start planet", required = true)
    private String start;

    @ApiModelProperty(value = "Player which sent the flight", required = true)
    private String player;

    @ApiModelProperty(value = "Destination planet", required = true)
    private String destination;

    @ApiModelProperty(value = "Arrival in round", required = true)
    private long arrivalInRound;

    @ApiModelProperty(value = "Approximated fleet size", required = true)
    private long approximatedFleetSize;

    public DetectedFlightResponse() {
    }

    public DetectedFlightResponse(String start, String player, String destination, long arrivalInRound, long approximatedFleetSize) {
        this.start = start;
        this.player = player;
        this.destination = destination;
        this.arrivalInRound = arrivalInRound;
        this.approximatedFleetSize = approximatedFleetSize;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getArrivalInRound() {
        return arrivalInRound;
    }

    public void setArrivalInRound(long arrivalInRound) {
        this.arrivalInRound = arrivalInRound;
    }

    public long getApproximatedFleetSize() {
        return approximatedFleetSize;
    }

    public void setApproximatedFleetSize(long approximatedFleetSize) {
        this.approximatedFleetSize = approximatedFleetSize;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("start", start)
                .add("player", player)
                .add("destination", destination)
                .add("arrivalInRound", arrivalInRound)
                .add("approximatedFleetSize", approximatedFleetSize)
                .toString();
    }
}
