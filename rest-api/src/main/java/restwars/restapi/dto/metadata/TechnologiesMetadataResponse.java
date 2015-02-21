package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Technologies metadata")
public class TechnologiesMetadataResponse {
    @ApiModelProperty(value = "List of technologies metadata", required = true)
    private List<TechnologyMetadataResponse> data;

    public TechnologiesMetadataResponse() {
    }

    public TechnologiesMetadataResponse(List<TechnologyMetadataResponse> data) {
        this.data = data;
    }

    public List<TechnologyMetadataResponse> getData() {
        return data;
    }

    public void setData(List<TechnologyMetadataResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechnologiesMetadataResponse that = (TechnologiesMetadataResponse) o;

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
