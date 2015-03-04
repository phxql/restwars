package restwars.storage.jooq;

import io.dropwizard.db.ManagedDataSource;
import restwars.UnrecoverableException;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages jOOQ unit of works.
 */
public class JooqUnitOfWorkService implements UnitOfWorkService {
    private static final ThreadLocal<UnitOfWork> CURRENT_UNIT_OF_WORK = new ThreadLocal<>();

    private final ManagedDataSource managedDataSource;
    private final JooqUnitOfWorkFactory jooqUnitOfWorkFactory;

    /**
     * Constructor.
     *
     * @param managedDataSource     Data source.
     * @param jooqUnitOfWorkFactory Factory for unit of works.
     */
    public JooqUnitOfWorkService(ManagedDataSource managedDataSource, JooqUnitOfWorkFactory jooqUnitOfWorkFactory) {
        this.managedDataSource = managedDataSource;
        this.jooqUnitOfWorkFactory = jooqUnitOfWorkFactory;
    }

    @Override
    public UnitOfWork getCurrent() {
        UnitOfWork current = CURRENT_UNIT_OF_WORK.get();
        if (current == null) {
            throw new IllegalStateException("No current unit of work found");
        }

        return current;
    }

    @Override
    public UnitOfWork start() {
        try {
            Connection connection = managedDataSource.getConnection();
            connection.setAutoCommit(false);
            UnitOfWork unitOfWork = jooqUnitOfWorkFactory.create(connection);

            CURRENT_UNIT_OF_WORK.set(unitOfWork);

            return unitOfWork;
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot start transaction", e);
        }
    }

    @Override
    public void commit() {
        JooqUnitOfWork jooqUnitOfWork = getCurrentJooq();

        try {
            CURRENT_UNIT_OF_WORK.remove();

            jooqUnitOfWork.getConnection().commit();
            jooqUnitOfWork.getConnection().close();
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot commit transaction", e);
        }
    }

    @Override
    public void abort() {
        JooqUnitOfWork jooqUnitOfWork = getCurrentJooq();

        try {
            CURRENT_UNIT_OF_WORK.remove();

            jooqUnitOfWork.getConnection().rollback();
            jooqUnitOfWork.getConnection().close();
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot rollback transaction", e);
        }
    }

    @Override
    public boolean hasCurrent() {
        return CURRENT_UNIT_OF_WORK.get() != null;
    }

    private JooqUnitOfWork getCurrentJooq() {
        return (JooqUnitOfWork) getCurrent();
    }
}
