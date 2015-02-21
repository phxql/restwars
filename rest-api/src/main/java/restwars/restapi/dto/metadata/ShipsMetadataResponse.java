package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Ships metadata")
public class ShipsMetadataResponse {
    @ApiModelProperty(value = "List of ships metadata", required = true)
    private List<ShipMetadataResponse> data;

    public ShipsMetadataResponse() {
    }

    public ShipsMetadataResponse(List<ShipMetadataResponse> data) {
        this.data = data;
    }

    public List<ShipMetadataResponse> getData() {
        return data;
    }

    public void setData(List<ShipMetadataResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipsMetadataResponse that = (ShipsMetadataResponse) o;

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
