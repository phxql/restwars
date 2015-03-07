package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;

public class RoundResponse {
    private long round;

    public RoundResponse(long round) {
        this.round = round;
    }

    public void setRound(long round) {
        this.round = round;
    }

    public long getRound() {
        return round;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundResponse that = (RoundResponse) o;

        return Objects.equal(this.round, that.round);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(round);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("round", round)
                .toString();
    }
}
