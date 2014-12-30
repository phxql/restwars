package restwars.storage.jooq;

import io.dropwizard.db.ManagedDataSource;
import restwars.UnrecoverableException;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;

import java.sql.Connection;
import java.sql.SQLException;

public class JooqUnitOfWorkService implements UnitOfWorkService {
    private final ManagedDataSource managedDataSource;

    private static final ThreadLocal<UnitOfWork> currentUnitOfWork = new ThreadLocal<>();

    public JooqUnitOfWorkService(ManagedDataSource managedDataSource) {
        this.managedDataSource = managedDataSource;
    }

    @Override
    public UnitOfWork getCurrent() {
        UnitOfWork current = currentUnitOfWork.get();
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
            JooqUnitOfWork unitOfWork = new JooqUnitOfWork(connection);

            currentUnitOfWork.set(unitOfWork);

            return unitOfWork;
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot start transaction", e);
        }
    }

    @Override
    public void commit() {
        JooqUnitOfWork jooqUnitOfWork = getCurrentJooq();

        try {
            currentUnitOfWork.remove();

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
            currentUnitOfWork.remove();

            jooqUnitOfWork.getConnection().rollback();
            jooqUnitOfWork.getConnection().close();
        } catch (SQLException e) {
            throw new UnrecoverableException("Cannot rollback transaction", e);
        }
    }

    private JooqUnitOfWork getCurrentJooq() {
        return (JooqUnitOfWork) getCurrent();
    }
}