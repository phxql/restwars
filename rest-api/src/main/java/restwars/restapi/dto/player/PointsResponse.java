package restwars.restapi.dto.player;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Points")
public class PointsResponse {
    @ApiModelProperty(value = "List of points", required = true)
    private List<PointResponse> data;

    public PointsResponse() {
    }

    public PointsResponse(List<PointResponse> data) {
        this.data = data;
    }

    public List<PointResponse> getData() {
        return data;
    }

    public void setData(List<PointResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointsResponse that = (PointsResponse) o;

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
