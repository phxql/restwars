package restwars.service.infrastructure.impl;

import com.google.common.base.Preconditions;
import restwars.service.infrastructure.RoundDAO;
import restwars.service.infrastructure.RoundService;

import javax.inject.Inject;

public class RoundServiceImpl implements RoundService {
    private final RoundDAO roundDAO;

    @Inject
    public RoundServiceImpl(RoundDAO roundDAO) {
        this.roundDAO = Preconditions.checkNotNull(roundDAO, "roundDAO");
    }

    @Override
    public long getCurrentRound() {
        // TODO: Implement caching

        return roundDAO.getRound();
    }

    @Override
    public long nextRound() {
        long newRound = getCurrentRound() + 1;
        roundDAO.updateRound(newRound);

        return newRound;
    }
}
