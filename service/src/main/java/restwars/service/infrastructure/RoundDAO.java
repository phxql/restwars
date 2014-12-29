package restwars.service.infrastructure;

/**
 * DAO for rounds.
 */
public interface RoundDAO {
    /**
     * Returns the current round.
     *
     * @return Round.
     */
    long getRound();

    /**
     * Updates the round to the given value.
     *
     * @param round New value.
     */
    void updateRound(long round);
}
