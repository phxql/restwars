package restwars.service.infrastructure.impl;

import restwars.service.infrastructure.RoundService;

import java.util.concurrent.atomic.AtomicLong;

public class RoundServiceImpl implements RoundService {
    private final AtomicLong currentRound = new AtomicLong(1);

    @Override
    public long getCurrentRound() {
        return currentRound.get();
    }

    @Override
    public void nextRound() {
        currentRound.incrementAndGet();
    }
}
