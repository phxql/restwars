package restwars.rest.integration.locking;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.*;
import restwars.service.infrastructure.LockService;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.List;

/**
 * A jersey filter which gets the global reader lock before handling the request and releases it afterwards.
 */
@Provider
public class LockingFilter implements ContainerRequestFilter, ContainerResponseFilter, ResourceFilter, ResourceFilterFactory {
    private final LockService lockService;

    @Inject
    public LockingFilter(LockService lockService) {
        this.lockService = Preconditions.checkNotNull(lockService, "lockService");
    }

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        lockService.beforeRequest();

        return request;
    }

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        lockService.afterRequest();

        return response;
    }

    @Override
    public ContainerRequestFilter getRequestFilter() {
        return this;
    }

    @Override
    public ContainerResponseFilter getResponseFilter() {
        return this;
    }

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        return Collections.singletonList(this);
    }
}
