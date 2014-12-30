package restwars.service.infrastructure;

/**
 * DAO for rounds.
 */
public interface RoundDAO {
    /**
     * Initial round.
     */
    public static final long INITIAL_ROUND = 1L;

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
