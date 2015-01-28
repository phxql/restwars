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

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class DatabaseTest {
    private static final String JDBC_URL = "jdbc:h2:mem:test";
    public static final String MIGRATION_FILE = "migrations.xml";

    private Handle handle;
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

    protected abstract Scenario getScenario();

    private void createSchema() throws LiquibaseException {
        Liquibase liquibase = new Liquibase(MIGRATION_FILE, new ClassLoaderResourceAccessor(DatabaseTest.class.getClassLoader()), new JdbcConnection(handle.getConnection()));
        liquibase.update("");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        handle.close();
    }

    protected UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    protected List<Map<String, Object>> select(String sql, Object... params) throws SQLException {
        return handle.select(sql, params);
    }
}
