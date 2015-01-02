package restwars.restapi.dto.technology;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Technology")
public class TechnologyResponse {
    @ApiModelProperty(value = "Technology type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

    public TechnologyResponse(String type, int level) {
        this.type = Preconditions.checkNotNull(type, "type");
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
