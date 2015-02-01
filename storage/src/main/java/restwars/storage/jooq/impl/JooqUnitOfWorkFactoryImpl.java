package restwars.storage.jooq.impl;

import restwars.service.unitofwork.UnitOfWork;
import restwars.storage.jooq.JooqUnitOfWork;
import restwars.storage.jooq.JooqUnitOfWorkFactory;

import java.sql.Connection;

public class JooqUnitOfWorkFactoryImpl implements JooqUnitOfWorkFactory {
    @Override
    public UnitOfWork create(Connection connection) {
        return new JooqUnitOfWork(connection);
    }
}
