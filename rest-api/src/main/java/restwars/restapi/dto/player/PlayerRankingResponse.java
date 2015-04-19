package restwars.restapi.dto.player;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Player ranking")
public class PlayerRankingResponse {
    @ApiModelProperty(value = "List of rankings", required = true)
    private List<PlayerRankingEntryResponse> data;

    public PlayerRankingResponse() {
    }

    public PlayerRankingResponse(List<PlayerRankingEntryResponse> data) {
        this.data = data;
    }

    public List<PlayerRankingEntryResponse> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerRankingResponse that = (PlayerRankingResponse) o;

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
