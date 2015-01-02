package restwars.rest.api.metadata;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.service.ship.FlightType;

@ApiModel(description = "Flight type metadata")
public class FlightTypeMetadataResponse {
    @ApiModelProperty(value = "Flight type", required = true)
    private final String type;

    public FlightTypeMetadataResponse(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static FlightTypeMetadataResponse fromFlightType(FlightType flightType) {
        Preconditions.checkNotNull(flightType, "flightType");

        return new FlightTypeMetadataResponse(flightType.name());
    }
}
