package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Buildings metadata")
public class BuildingsMetadataResponse {
    @ApiModelProperty(value = "List of buildings metadata", required = true)
    private List<BuildingMetadataResponse> data;

    public BuildingsMetadataResponse() {
    }

    public BuildingsMetadataResponse(List<BuildingMetadataResponse> data) {
        this.data = data;
    }

    public List<BuildingMetadataResponse> getData() {
        return data;
    }

    public void setData(List<BuildingMetadataResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingsMetadataResponse that = (BuildingsMetadataResponse) o;

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
