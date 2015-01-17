package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Event type metadata")
public class EventTypeMetadataResponse {
    @ApiModelProperty(value = "Event type", required = true)
    private String type;

    @ApiModelProperty(value = "Description", required = true)
    private String description;

    public EventTypeMetadataResponse() {
    }

    public EventTypeMetadataResponse(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("description", description)
                .toString();
    }
}
