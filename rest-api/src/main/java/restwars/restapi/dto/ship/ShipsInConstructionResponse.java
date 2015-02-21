package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Ships in construction")
public class ShipsInConstructionResponse {
    @ApiModelProperty(value = "List of ships in construction", required = true)
    private List<ShipInConstructionResponse> data;

    public ShipsInConstructionResponse() {
    }

    public ShipsInConstructionResponse(List<ShipInConstructionResponse> data) {
        this.data = data;
    }

    public List<ShipInConstructionResponse> getData() {
        return data;
    }

    public void setData(List<ShipInConstructionResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipsInConstructionResponse that = (ShipsInConstructionResponse) o;

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
