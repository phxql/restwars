package restwars.restapi.dto.technology;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(description = "Researches a new technology")
public class ResearchRequest {
    @ApiModelProperty(value = "Technology type", required = true)
    @NotEmpty
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
