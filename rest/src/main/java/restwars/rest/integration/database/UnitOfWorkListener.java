package restwars.rest.integration.database;

import com.google.common.base.Preconditions;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.inject.Inject;

public class UnitOfWorkListener implements ApplicationEventListener {
    private final RequestEventListener requestEventListener;

    @Inject
    public UnitOfWorkListener(UnitOfWorkService unitOfWorkService) {
        Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");

        this.requestEventListener = new UnitOfWorkRequestListener(unitOfWorkService);
    }

    @Override
    public void onEvent(ApplicationEvent applicationEvent) {
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return requestEventListener;
    }

    private static class UnitOfWorkRequestListener implements RequestEventListener {
        private final UnitOfWorkService unitOfWorkService;

        public UnitOfWorkRequestListener(UnitOfWorkService unitOfWorkService) {
            this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
        }

        @Override
        public void onEvent(RequestEvent event) {
            if (event.getType() == RequestEvent.Type.RESOURCE_METHOD_START) {
                unitOfWorkService.start();
            } else if (event.getType() == RequestEvent.Type.ON_EXCEPTION) {
                unitOfWorkService.abort();
            } else if (event.getType() == RequestEvent.Type.RESP_FILTERS_START) {
                if (unitOfWorkService.hasCurrent()) {
                    unitOfWorkService.commit();
                }
            }
        }
    }
}
