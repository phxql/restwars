package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Detected flights")
public class DetectedFlightsResponse {
    @ApiModelProperty(value = "List of detected flights", required = true)
    private List<DetectedFlightResponse> data;

    public DetectedFlightsResponse() {
    }

    public DetectedFlightsResponse(List<DetectedFlightResponse> data) {
        this.data = data;
    }

    public List<DetectedFlightResponse> getData() {
        return data;
    }

    public void setData(List<DetectedFlightResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetectedFlightsResponse that = (DetectedFlightsResponse) o;

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
