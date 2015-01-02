package restwars.restapi.dto.building;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Building")
public class BuildingResponse {
    @ApiModelProperty(value = "Type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

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
}
