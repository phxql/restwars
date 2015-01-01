package restwars.rest.api.technology;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.service.technology.Technology;

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

    public static TechnologyResponse fromTechnology(Technology technology) {
        Preconditions.checkNotNull(technology, "technology");

        return new TechnologyResponse(technology.getType().toString(), technology.getLevel());
    }
}
