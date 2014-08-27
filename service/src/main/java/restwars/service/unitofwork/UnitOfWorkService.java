package restwars.service.unitofwork;

/**
 * Service which manages unit of works.
 */
public interface UnitOfWorkService {
    /**
     * Returns the current unit of work.
     *
     * @return Current unit of work.
     * @throws java.lang.IllegalStateException If no unit of work exists.
     */
    UnitOfWork getCurrent();

    /**
     * Starts a new unit of work and stores the unit of work as current unit of work.
     *
     * @return New unit of work.
     */
    UnitOfWork start();

    /**
     * Commits the current unit of work.
     * <p>
     * After the unit of work has been committed, no further actions can be executed on this unit of work. The current unit of work
     * is gone after this method call.
     */
    void commit();

    /**
     * Aborts the current unit of work.
     * <p>
     * After the unit of work has been committed, no further actions can be executed on this unit of work. The current unit of work
     * is gone after this method call.
     */
    void abort();
}
