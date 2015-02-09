package restwars.restapi.dto;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

@ApiModel(description = "General information")
public class GeneralInformationResponse {
    @ApiModelProperty(value = "Current round", required = true)
    private long round;

    @ApiModelProperty(value = "Start datetime of current round", required = true)
    private DateTime roundStarted;

    public GeneralInformationResponse() {
    }

    public GeneralInformationResponse(long round, DateTime roundStarted) {
        this.round = round;
        this.roundStarted = roundStarted;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }

    public DateTime getRoundStarted() {
        return roundStarted;
    }

    public void setRoundStarted(DateTime roundStarted) {
        this.roundStarted = roundStarted;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("round", round)
                .add("roundStarted", roundStarted)
                .toString();
    }
}
