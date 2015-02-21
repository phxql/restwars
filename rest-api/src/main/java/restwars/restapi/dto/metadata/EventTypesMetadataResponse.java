package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Event types metadata")
public class EventTypesMetadataResponse {
    @ApiModelProperty(value = "List of event types metadata", required = true)
    private List<EventTypeMetadataResponse> data;

    public EventTypesMetadataResponse() {
    }

    public EventTypesMetadataResponse(List<EventTypeMetadataResponse> data) {
        this.data = data;
    }

    public List<EventTypeMetadataResponse> getData() {
        return data;
    }

    public void setData(List<EventTypeMetadataResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventTypesMetadataResponse that = (EventTypesMetadataResponse) o;

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
