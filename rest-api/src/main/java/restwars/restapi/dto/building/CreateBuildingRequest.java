package restwars.restapi.dto.building;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(description = "Creates a building")
public class CreateBuildingRequest {
    @ApiModelProperty(value = "Type of building to constructBu", required = true)
    @NotEmpty
    private String type;

    public CreateBuildingRequest() {
    }

    public CreateBuildingRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .toString();
    }
}
