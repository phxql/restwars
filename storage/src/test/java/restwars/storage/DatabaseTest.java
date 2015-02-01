package restwars.storage;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.JooqUnitOfWork;
import restwars.storage.scenario.Scenario;

import java.util.List;
import java.util.Map;

/**
 * Base class for every database test.
 */
public abstract class DatabaseTest {
    /**
     * JDBC URL to the test database.
     */
    private static final String JDBC_URL = "jdbc:h2:mem:test";

    /**
     * Migration file.
     */
    private static final String MIGRATION_FILE = "migrations.xml";

    /**
     * Handle to the database.
     */
    private Handle handle;
    /**
     * Unit of work service.
     */
    private UnitOfWorkService unitOfWorkService;

    @BeforeMethod
    public void setUp() throws Exception {
        handle = DBI.open(JDBC_URL);
        createSchema();
        getScenario().create(handle);

        UnitOfWork unitOfWork = new JooqUnitOfWork(handle.getConnection());

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

    /**
     * Returns the scenario used for the test.
     *
     * @return Scenario used for the test.
     */
    protected abstract Scenario getScenario();

    private void createSchema() throws LiquibaseException {
        Liquibase liquibase = new Liquibase(MIGRATION_FILE, new ClassLoaderResourceAccessor(DatabaseTest.class.getClassLoader()), new JdbcConnection(handle.getConnection()));
        liquibase.update("");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        handle.close();
    }

    /**
     * Returns the unit of work service.
     *
     * @return Unit of work service.
     */
    protected UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    /**
     * Executes a SQL statement and returns the result set.
     *
     * @param sql    SQL statement to execute.
     * @param params Parameters to the SQL.
     * @return Result set.
     */
    protected List<Map<String, Object>> select(String sql, Object... params) {
        return handle.select(sql, params);
    }

    /**
     * Executes a SQL statement.
     *
     * @param sql    SQL statement to execute.
     * @param params Parameters to the SQL.
     */
    protected void execute(String sql, Object... params) {
        handle.execute(sql, params);
    }
}
