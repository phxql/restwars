package restwars.storage;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.JooqUnitOfWork;
import restwars.storage.scenario.Scenario;

import java.sql.*;

public abstract class DatabaseTest {
    private static final String JDBC_URL = "jdbc:h2:mem:test";
    public static final String MIGRATION_FILE = "migrations.xml";

    private Connection connection;
    private UnitOfWorkService unitOfWorkService;

    @BeforeMethod
    public void setUp() throws Exception {
        connection = DriverManager.getConnection(JDBC_URL);
        createSchema();
        getScenario().create(connection);

        UnitOfWork unitOfWork = new JooqUnitOfWork(connection);

        unitOfWorkService = new UnitOfWorkService() {
            @Override
            public UnitOfWork getCurrent() {
                return unitOfWork;
            }

            @Override
            public UnitOfWork start() {
                return unitOfWork;
            }

            @Override
            public void commit() {
            }

            @Override
            public void abort() {
            }
        };
    }

    protected abstract Scenario getScenario();

    private void createSchema() throws LiquibaseException {
        Liquibase liquibase = new Liquibase(MIGRATION_FILE, new ClassLoaderResourceAccessor(DatabaseTest.class.getClassLoader()), new JdbcConnection(connection));
        liquibase.update("");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        connection.close();
    }

    protected UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    protected ResultSet select(String sql, Object... params) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(String.format(sql, params));
    }
}
