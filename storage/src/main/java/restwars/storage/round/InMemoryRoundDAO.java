package restwars.storage.round;

import restwars.service.infrastructure.RoundDAO;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class InMemoryRoundDAO implements RoundDAO {
    private final AtomicLong round = new AtomicLong(1);

    @Override
    public long getRound() {
        return round.get();
    }

    @Override
    public void updateRound(long round) {
        this.round.set(round);
    }
}
