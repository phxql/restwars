package restwars.storage.jooq;

import com.google.common.base.Preconditions;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import restwars.service.unitofwork.UnitOfWork;

import java.sql.Connection;

/**
 * jOOQ unit of work implementation.
 */
public class JooqUnitOfWork implements UnitOfWork {
    private final Connection connection;
    private final DSLContext dslContext;

    /**
     * Constructor.
     *
     * @param connection JDBC connection.
     */
    public JooqUnitOfWork(Connection connection) {
        this.connection = Preconditions.checkNotNull(connection, "connection");
        this.dslContext = DSL.using(connection);
    }

    public DSLContext getDslContext() {
        return dslContext;
    }

    public Connection getConnection() {
        return connection;
    }
}
