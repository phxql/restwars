package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Flight type metadata")
public class FlightTypeMetadataResponse {
    @ApiModelProperty(value = "Flight type", required = true)
    private String type;

    public FlightTypeMetadataResponse() {
    }

    public FlightTypeMetadataResponse(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .toString();
    }
}
