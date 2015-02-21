package restwars.restapi.dto.planet;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Planet scans")
public class PlanetScansResponse {
    @ApiModelProperty(value = "List of planet scans", required = true)
    private List<PlanetScanResponse> data;

    public PlanetScansResponse() {
    }

    public PlanetScansResponse(List<PlanetScanResponse> data) {
        this.data = data;
    }

    public List<PlanetScanResponse> getData() {
        return data;
    }

    public void setData(List<PlanetScanResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetScansResponse that = (PlanetScansResponse) o;

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
