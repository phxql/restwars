package restwars.restapi.dto.technology;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Technology")
public class TechnologyResponse {
    @ApiModelProperty(value = "Technology type", required = true)
    private String type;

    @ApiModelProperty(value = "Level", required = true)
    private int level;

    public TechnologyResponse() {
    }

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
