package restwars.storage;

import com.google.common.base.Preconditions;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.jooq.SQLDialect;
import restwars.UnrecoverableException;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class JdbcConnection {
    private final Connection connection;
    private final SQLDialect sqlDialect;

    public JdbcConnection(String driverClass, String url, String username, String password, String sqlDialect) {
        Preconditions.checkNotNull(sqlDialect, "sqlDialect");

        this.sqlDialect = SQLDialect.valueOf(sqlDialect);
        try {
            Class.forName(driverClass).newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            throw new UnrecoverableException("Failed to create database connection", e);
        }
    }

    public SQLDialect getSqlDialect() {
        return sqlDialect;
    }

    public Connection getConnection() {
        return connection;
    }

    public void updateSchema() {
        // TODO: Move schema updating into a dropqizard command

        DatabaseConnection liquibaseConnection = new liquibase.database.jvm.JdbcConnection(connection);

        try {
            new Liquibase("migration.xml", new ClassLoaderResourceAccessor(), liquibaseConnection).update("");
        } catch (LiquibaseException e) {
            throw new UnrecoverableException("Failed to update schema", e);
        }
    }
}
