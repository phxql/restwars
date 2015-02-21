package restwars.restapi.dto.planet;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Planets")
public class PlanetsResponse {
    @ApiModelProperty(value = "List of planets", required = true)
    private List<PlanetResponse> data;

    public PlanetsResponse(List<PlanetResponse> data) {
        this.data = data;
    }

    public PlanetsResponse() {
    }

    public List<PlanetResponse> getData() {
        return data;
    }

    public void setData(List<PlanetResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetsResponse that = (PlanetsResponse) o;

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
