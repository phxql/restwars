package restwars.restapi.dto.technology;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Researches")
public class ResearchesResponse {
    @ApiModelProperty(value = "List of researches", required = true)
    private List<ResearchResponse> data;

    public ResearchesResponse() {
    }

    public ResearchesResponse(List<ResearchResponse> data) {
        this.data = data;
    }

    public List<ResearchResponse> getData() {
        return data;
    }

    public void setData(List<ResearchResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResearchesResponse that = (ResearchesResponse) o;

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
