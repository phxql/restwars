package restwars.storage.jooq;

import com.google.common.base.Preconditions;
import org.jooq.DSLContext;
import restwars.service.unitofwork.UnitOfWork;

public abstract class AbstractJooqDAO {
    protected static DSLContext context(UnitOfWork uow) {
        Preconditions.checkNotNull(uow, "uow");

        if (!(uow instanceof JooqUnitOfWork)) {
            throw new IllegalArgumentException("Expected a JooqUnitOfWork, found " + uow.getClass());
        }

        return ((JooqUnitOfWork) uow).getDslContext();
    }

}
