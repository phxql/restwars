package restwars.restapi.dto.metadata;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

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
}
