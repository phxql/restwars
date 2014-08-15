package restwars.rest.integration;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.dispatch.RequestDispatcher;
import restwars.service.unitofwork.UnitOfWorkService;

public class UnitOfWorkRequestDispatcher implements RequestDispatcher {
    private final RequestDispatcher dispatcher;
    private final UnitOfWorkService unitOfWorkService;

    public UnitOfWorkRequestDispatcher(UnitOfWorkService unitOfWorkService, RequestDispatcher dispatcher) {
        this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
        this.dispatcher = Preconditions.checkNotNull(dispatcher, "dispatcher");
    }

    @Override
    public void dispatch(Object resource, HttpContext context) {
        unitOfWorkService.start();
        try {
            dispatcher.dispatch(resource, context);
            unitOfWorkService.commit();
        } catch (Exception e) {
            unitOfWorkService.abort();
            this.<RuntimeException>rethrow(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Exception> void rethrow(Exception e) throws E {
        throw (E) e;
    }
}
