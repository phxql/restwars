package restwars.rest.integration.database;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.dispatch.RequestDispatcher;
import restwars.service.unitofwork.UnitOfWorkService;

/**
 * Starts a unit of work before processing the request and commits the unit of work after request. If the request is
 * aborted, the unit of work will also be aborted.
 */
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
