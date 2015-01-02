package restwars.restapi.dto.ship;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Flight")
public class FlightResponse {
    @ApiModelProperty(value = "Destination planet", required = true)
    private final String destination;

    @ApiModelProperty(value = "Started in round", required = true)
    private final long startedInRound;

    @ApiModelProperty(value = "Arrival in round", required = true)
    private final long arrivalInRound;

    @ApiModelProperty(value = "Ships on the flight", required = true)
    private final List<ShipResponse> ships;

    @ApiModelProperty(value = "Type", required = true)
    private final String type;

    @ApiModelProperty(value = "Direction", required = true)
    private final String direction;

    public FlightResponse(String destination, long startedInRound, long arrivalInRound, List<ShipResponse> ships, String type, String direction) {
        this.destination = destination;
        this.startedInRound = startedInRound;
        this.arrivalInRound = arrivalInRound;
        this.ships = ships;
        this.type = type;
        this.direction = direction;
    }

    public String getDestination() {
        return destination;
    }

    public long getStartedInRound() {
        return startedInRound;
    }

    public long getArrivalInRound() {
        return arrivalInRound;
    }

    public List<ShipResponse> getShips() {
        return ships;
    }

    public String getType() {
        return type;
    }

    public String getDirection() {
        return direction;
    }
}
