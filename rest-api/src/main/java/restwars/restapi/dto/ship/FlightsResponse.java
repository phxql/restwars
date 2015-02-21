package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Flights")
public class FlightsResponse {
    @ApiModelProperty(value = "List of flights", required = true)
    private List<FlightResponse> data;

    public FlightsResponse(List<FlightResponse> data) {
        this.data = data;
    }

    public FlightsResponse() {
    }

    public List<FlightResponse> getData() {
        return data;
    }

    public void setData(List<FlightResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightsResponse that = (FlightsResponse) o;

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
