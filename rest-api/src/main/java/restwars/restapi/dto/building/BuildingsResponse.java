package restwars.restapi.dto.building;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Buildings")
public class BuildingsResponse {
    @ApiModelProperty(value = "List of buildings", required = true)
    private List<BuildingResponse> data;

    public BuildingsResponse() {
    }

    public BuildingsResponse(List<BuildingResponse> data) {
        this.data = data;
    }

    public List<BuildingResponse> getData() {
        return data;
    }

    public void setData(List<BuildingResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingsResponse that = (BuildingsResponse) o;

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
