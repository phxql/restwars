package restwars.restapi.dto.building;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Construction sites")
public class ConstructionSitesResponse {
    @ApiModelProperty(value = "List of construction sites", required = true)
    private List<ConstructionSiteResponse> data;

    public ConstructionSitesResponse() {
    }

    public ConstructionSitesResponse(List<ConstructionSiteResponse> data) {
        this.data = data;
    }

    public List<ConstructionSiteResponse> getData() {
        return data;
    }

    public void setData(List<ConstructionSiteResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstructionSitesResponse that = (ConstructionSitesResponse) o;

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
