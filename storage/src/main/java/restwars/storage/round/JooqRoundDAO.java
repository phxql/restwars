package restwars.storage.round;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.RoundDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.RoundRecord;

import javax.inject.Inject;

import static restwars.storage.jooq.Tables.ROUND;

public class JooqRoundDAO extends AbstractJooqDAO implements RoundDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqRoundDAO.class);

    /**
     * Initial round.
     */
    private static final long INITIAL_ROUND = 1L;

    @Inject
    public JooqRoundDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public long getRound() {
        RoundRecord record = context()
                .selectFrom(ROUND)
                .fetchOne();

        if (record == null) {
            init();
            return INITIAL_ROUND;
        }

        return record.getCurrentRound();
    }

    private void init() {
        LOGGER.debug("Initializing round table");

        context()
                .insertInto(ROUND, ROUND.CURRENT_ROUND)
                .values(INITIAL_ROUND)
                .execute();
    }

    @Override
    public void updateRound(long round) {
        LOGGER.debug("Updating round to {}", round);

        context()
                .update(ROUND)
                .set(ROUND.CURRENT_ROUND, round)
                .execute();
    }
}
