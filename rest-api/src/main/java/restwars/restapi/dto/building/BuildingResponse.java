package restwars.restapi.dto.building;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Building")
public class BuildingResponse {
    @ApiModelProperty(value = "Type", required = true)
    private String type;

    @ApiModelProperty(value = "Level", required = true)
    private int level;

    public BuildingResponse() {
    }

    public BuildingResponse(String type, int level) {
        this.type = type;
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("level", level)
                .toString();
    }
}
