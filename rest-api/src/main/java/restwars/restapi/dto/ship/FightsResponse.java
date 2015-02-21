package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Fights")
public class FightsResponse {
    @ApiModelProperty(value = "List of fights", required = true)
    private List<FightResponse> data;

    public FightsResponse() {
    }

    public FightsResponse(List<FightResponse> data) {
        this.data = data;
    }

    public List<FightResponse> getData() {
        return data;
    }

    public void setData(List<FightResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FightsResponse that = (FightsResponse) o;

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
