package restwars.service.ship;

/**
 * DAO for fights.
 */
public interface FightDAO {
    /**
     * Inserts the given fight.
     *
     * @param fight Fight to insert.
     */
    void insert(Fight fight);
}
