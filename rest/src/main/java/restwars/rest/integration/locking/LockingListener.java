package restwars.rest.integration.locking;

import restwars.service.infrastructure.LockService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class LockingListener implements ContainerRequestFilter, ContainerResponseFilter {
    private final LockService lockService;

    @Inject
    public LockingListener(LockService lockService) {
        this.lockService = lockService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        lockService.beforeRequest();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        lockService.afterRequest();
    }
}
