package restwars.service.infrastructure.impl;

import restwars.service.infrastructure.LockService;

import javax.inject.Singleton;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Singleton
public class LockServiceImpl implements LockService {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void beforeRequest() {
        readWriteLock.readLock().lock();
    }

    @Override
    public void afterRequest() {
        readWriteLock.readLock().unlock();
    }

    @Override
    public void beforeClock() {
        readWriteLock.writeLock().lock();
    }

    @Override
    public void afterClock() {
        readWriteLock.writeLock().unlock();
    }
}
