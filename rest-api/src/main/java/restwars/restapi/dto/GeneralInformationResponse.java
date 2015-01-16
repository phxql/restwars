package restwars.restapi.dto;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "General information")
public class GeneralInformationResponse {
    @ApiModelProperty(value = "Current round", required = true)
    private long round;

    public GeneralInformationResponse() {
    }

    public GeneralInformationResponse(long round) {
        this.round = round;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("round", round)
                .toString();
    }
}
