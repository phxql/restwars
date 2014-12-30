package restwars.storage.jooq;

import com.google.common.base.Preconditions;
import org.jooq.DSLContext;
import restwars.service.unitofwork.UnitOfWork;
import restwars.service.unitofwork.UnitOfWorkService;

public abstract class AbstractJooqDAO {
    private final UnitOfWorkService unitOfWorkService;

    public AbstractJooqDAO(UnitOfWorkService unitOfWorkService) {
        this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
    }

    protected DSLContext context() {
        UnitOfWork uow = unitOfWorkService.getCurrent();

        if (!(uow instanceof JooqUnitOfWork)) {
            throw new IllegalArgumentException("Expected a JooqUnitOfWork, found " + uow.getClass());
        }

        return ((JooqUnitOfWork) uow).getDslContext();
    }

}
