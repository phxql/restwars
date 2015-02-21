package restwars.restapi.dto.technology;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Technologies")
public class TechnologiesResponse {
    @ApiModelProperty(value = "List of technologies", required = true)
    private List<TechnologyResponse> data;

    public TechnologiesResponse(List<TechnologyResponse> data) {
        this.data = data;
    }

    public TechnologiesResponse() {
    }

    public List<TechnologyResponse> getData() {
        return data;
    }

    public void setData(List<TechnologyResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechnologiesResponse that = (TechnologiesResponse) o;

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

