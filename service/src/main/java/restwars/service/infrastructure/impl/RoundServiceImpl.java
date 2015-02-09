package restwars.service.infrastructure.impl;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import restwars.service.infrastructure.DateTimeProvider;
import restwars.service.infrastructure.RoundDAO;
import restwars.service.infrastructure.RoundService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class RoundServiceImpl implements RoundService {
    private final RoundDAO roundDAO;
    private final DateTimeProvider dateTimeProvider;
    private final AtomicReference<DateTime> currentRoundStarted = new AtomicReference<>();

    @Override
    public void initialize() {
        currentRoundStarted.set(dateTimeProvider.now());
    }

    @Inject
    public RoundServiceImpl(RoundDAO roundDAO, DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = Preconditions.checkNotNull(dateTimeProvider, "dateTimeProvider");
        this.roundDAO = Preconditions.checkNotNull(roundDAO, "roundDAO");
    }

    @Override
    public long getCurrentRound() {
        // TODO: Performance - Implement caching

        return roundDAO.getRound();
    }

    @Override
    public long nextRound() {
        long newRound = getCurrentRound() + 1;

        roundDAO.updateRound(newRound);
        currentRoundStarted.set(dateTimeProvider.now());

        return newRound;
    }

    @Override
    public DateTime getCurrentRoundStarted() {
        return currentRoundStarted.get();
    }
}
