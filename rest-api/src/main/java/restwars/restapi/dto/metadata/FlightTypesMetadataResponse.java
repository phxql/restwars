package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Flight types metadata")
public class FlightTypesMetadataResponse {
    @ApiModelProperty(value = "List of flight types metadata", required = true)
    private List<FlightTypeMetadataResponse> data;

    public FlightTypesMetadataResponse() {
    }

    public FlightTypesMetadataResponse(List<FlightTypeMetadataResponse> data) {
        this.data = data;
    }

    public List<FlightTypeMetadataResponse> getData() {
        return data;
    }

    public void setData(List<FlightTypeMetadataResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightTypesMetadataResponse that = (FlightTypesMetadataResponse) o;

        return Objects.equal(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("data", data)
                .toString();
    }
}
