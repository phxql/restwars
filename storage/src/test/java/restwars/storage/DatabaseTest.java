package restwars.storage;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.JooqUnitOfWork;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTest {
    private static final String JDBC_URL = "jdbc:h2:mem:test";
    public static final String MIGRATION_FILE = "migrations.xml";

    private Connection connection;
    private UnitOfWorkService unitOfWorkService;

    @BeforeMethod
    public void setUp() throws Exception {
        connection = DriverManager.getConnection(JDBC_URL);
        createSchema();

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

    private void createSchema() throws LiquibaseException {
        Liquibase liquibase = new Liquibase(MIGRATION_FILE, new ClassLoaderResourceAccessor(DatabaseTest.class.getClassLoader()), new JdbcConnection(connection));
        liquibase.update("");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        connection.close();
    }

    protected void prepare(String... files) throws IOException, SQLException {
        try (Statement statement = connection.createStatement()) {
            for (String file : files) {
                try (InputStream stream = DatabaseTest.class.getResourceAsStream("/fixture/" + file)) {
                    String content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                    statement.execute(content);
                }
            }
        }
    }

    protected UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    protected Connection getConnection() {
        return connection;
    }
}
