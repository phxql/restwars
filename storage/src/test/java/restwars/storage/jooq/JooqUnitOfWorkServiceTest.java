package restwars.storage.jooq;

import io.dropwizard.db.ManagedDataSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.service.unitofwork.UnitOfWork;

import java.sql.Connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class JooqUnitOfWorkServiceTest {
    private JooqUnitOfWorkService sut;
    private ManagedDataSource managedDataSource;
    private JooqUnitOfWorkFactory jooqUnitOfWorkFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        managedDataSource = mock(ManagedDataSource.class);
        jooqUnitOfWorkFactory = mock(JooqUnitOfWorkFactory.class);
        sut = new JooqUnitOfWorkService(managedDataSource, jooqUnitOfWorkFactory);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testGetCurrent() throws Exception {
        sut.getCurrent();
    }

    @Test
    public void testStart() throws Exception {
        Connection connection = mock(Connection.class);

        JooqUnitOfWork unitOfWork = mock(JooqUnitOfWork.class);
        when(unitOfWork.getConnection()).thenReturn(connection);
        when(jooqUnitOfWorkFactory.create(connection)).thenReturn(unitOfWork);
        when(managedDataSource.getConnection()).thenReturn(connection);

        UnitOfWork actual = sut.start();

        verify(connection).setAutoCommit(false);
        assertThat(actual, is(unitOfWork));
    }

    @Test
    public void testCommit() throws Exception {
        Connection connection = mock(Connection.class);

        JooqUnitOfWork unitOfWork = mock(JooqUnitOfWork.class);
        when(unitOfWork.getConnection()).thenReturn(connection);
        when(jooqUnitOfWorkFactory.create(connection)).thenReturn(unitOfWork);
        when(managedDataSource.getConnection()).thenReturn(connection);

        sut.start();

        sut.commit();

        verify(connection).commit();
        verify(connection).close();
    }

    @Test
    public void testAbort() throws Exception {
        Connection connection = mock(Connection.class);

        JooqUnitOfWork unitOfWork = mock(JooqUnitOfWork.class);
        when(unitOfWork.getConnection()).thenReturn(connection);
        when(jooqUnitOfWorkFactory.create(connection)).thenReturn(unitOfWork);
        when(managedDataSource.getConnection()).thenReturn(connection);

        sut.start();

        sut.abort();

        verify(connection).rollback();
        verify(connection).close();
    }
}