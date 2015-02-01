package restwars.storage.jooq;

import restwars.service.unitofwork.UnitOfWork;

import java.sql.Connection;

/**
 * A factory for {@link restwars.storage.jooq.JooqUnitOfWorkFactory}.
 */
public interface JooqUnitOfWorkFactory {
    /**
     * Creates a new {@link JooqUnitOfWork} with the given connection.
     *
     * @param connection Connection
     * @return JooqUnitOfWork.
     */
    UnitOfWork create(Connection connection);
}
