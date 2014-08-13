package restwars.service.unitofwork;

/**
 * Service which manages unit of works.
 */
public interface UnitOfWorkService {
    /**
     * Starts a new unit of work.
     *
     * @return New unit of work.
     */
    UnitOfWork start();

    /**
     * Commits the unit of work.
     * <p>
     * After the unit of work has been committed, no further actions can be executed on this unit of work.
     *
     * @param unitOfWork Unit of work to commit.
     */
    void commit(UnitOfWork unitOfWork);

    /**
     * Aborts the unit of work.
     * <p>
     * After the unit of work has been committed, no further actions can be executed on this unit of work.
     *
     * @param unitOfWork Unit of work to abort.
     */
    void abort(UnitOfWork unitOfWork);
}
