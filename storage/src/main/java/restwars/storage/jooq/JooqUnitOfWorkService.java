package restwars.storage.jooq;

import com.google.common.base.Preconditions;
import io.dropwizard.db.ManagedDataSource;
import restwars.UnrecoverableException;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;

import java.sql.Connection;
import java.sql.SQLException;

public class JooqUnitOfWorkService implements UnitOfWorkService {
    private final ManagedDataSource managedDataSource;

    public JooqUnitOfWorkService(ManagedDataSource managedDataSource) {
        this.managedDataSource = managedDataSource;
    }

    private Connection createConnection() {
        try {
            return managedDataSource.getConnection();
        } catch (SQLException e) {
            throw new UnrecoverableException("Failed to create database connection", e);
        }
    }

    @Override
    public UnitOfWork start() {
        try {
            Connection connection = createConnection();
            connection.setAutoCommit(false);
            return new JooqUnitOfWork(connection);
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot start transaction", e);
        }
    }

    @Override
    public void commit(UnitOfWork unitOfWork) {
        Preconditions.checkNotNull(unitOfWork, "unitOfWork");
        JooqUnitOfWork jooqUnitOfWork = checkType(unitOfWork);

        try {
            jooqUnitOfWork.getConnection().commit();
            jooqUnitOfWork.getConnection().close();
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot commit transaction", e);
        }
    }

    @Override
    public void abort(UnitOfWork unitOfWork) {
        Preconditions.checkNotNull(unitOfWork, "unitOfWork");
        JooqUnitOfWork jooqUnitOfWork = checkType(unitOfWork);

        try {
            jooqUnitOfWork.getConnection().rollback();
            jooqUnitOfWork.getConnection().close();
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot rollback transaction", e);
        }
    }

    /**
     * Checks that the given unit of work is of type {@link restwars.service.unitofwork.UnitOfWork}. Throws an
     * {@link java.lang.IllegalArgumentException} if it's not.
     *
     * @param unitOfWork Unit of work to check.
     * @return A {@link JooqUnitOfWork}.
     */
    private static JooqUnitOfWork checkType(UnitOfWork unitOfWork) {
        assert unitOfWork != null;

        if (!(unitOfWork instanceof JooqUnitOfWork)) {
            throw new IllegalArgumentException("Expected a JooqUnitOfWork, found " + unitOfWork.getClass());
        }

        return (JooqUnitOfWork) unitOfWork;
    }
}
