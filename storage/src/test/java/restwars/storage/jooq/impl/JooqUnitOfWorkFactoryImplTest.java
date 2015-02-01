package restwars.storage.jooq.impl;

import org.testng.annotations.Test;
import restwars.service.unitofwork.UnitOfWork;
import restwars.storage.jooq.JooqUnitOfWork;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JooqUnitOfWorkFactoryImplTest {
    @Test
    public void testCreate() throws Exception {
        JooqUnitOfWorkFactoryImpl sut = new JooqUnitOfWorkFactoryImpl();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            UnitOfWork unitOfWork = sut.create(connection);

            assertThat(unitOfWork, is(notNullValue()));
            assertThat(unitOfWork, instanceOf(JooqUnitOfWork.class));
        }
    }
}