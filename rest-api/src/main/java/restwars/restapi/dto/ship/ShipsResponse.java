package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Ships")
public class ShipsResponse {
    @ApiModelProperty(value = "List of ships", required = true)
    private List<ShipResponse> data;

    public ShipsResponse(List<ShipResponse> data) {
        this.data = data;
    }

    public ShipsResponse() {
    }

    public List<ShipResponse> getData() {
        return data;
    }

    public void setData(List<ShipResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipsResponse that = (ShipsResponse) o;

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
