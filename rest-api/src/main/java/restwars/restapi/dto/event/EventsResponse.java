package restwars.restapi.dto.event;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Events")
public class EventsResponse {
    @ApiModelProperty(value = "List of events", required = true)
    private List<EventResponse> data;

    public EventsResponse() {
    }

    public EventsResponse(List<EventResponse> data) {
        this.data = data;
    }

    public List<EventResponse> getData() {
        return data;
    }

    public void setData(List<EventResponse> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventsResponse that = (EventsResponse) o;

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
