package restwars.restapi.dto.ship;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Flight")
public class FlightResponse {
    @ApiModelProperty(value = "Destination planet", required = true)
    private String destination;

    @ApiModelProperty(value = "Started in round", required = true)
    private long startedInRound;

    @ApiModelProperty(value = "Arrival in round", required = true)
    private long arrivalInRound;

    @ApiModelProperty(value = "Ships on the flight", required = true)
    private List<ShipResponse> ships;

    @ApiModelProperty(value = "Type", required = true)
    private String type;

    @ApiModelProperty(value = "Direction", required = true)
    private String direction;

    public FlightResponse() {
    }

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

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartedInRound(long startedInRound) {
        this.startedInRound = startedInRound;
    }

    public void setArrivalInRound(long arrivalInRound) {
        this.arrivalInRound = arrivalInRound;
    }

    public void setShips(List<ShipResponse> ships) {
        this.ships = ships;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
