package restwars.rest.integration;

import com.google.common.base.Preconditions;
import com.sun.jersey.spi.container.ResourceMethodDispatchAdapter;
import com.sun.jersey.spi.container.ResourceMethodDispatchProvider;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.ws.rs.ext.Provider;

@Provider
public class UnitOfWorkResourceMethodDispatchAdapter implements ResourceMethodDispatchAdapter {
    private final UnitOfWorkService unitOfWorkService;

    public UnitOfWorkResourceMethodDispatchAdapter(UnitOfWorkService unitOfWorkService) {
        this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
    }

    @Override
    public ResourceMethodDispatchProvider adapt(ResourceMethodDispatchProvider provider) {
        return new UnitOfWorkResourceMethodDispatchProvider(unitOfWorkService, provider);
    }
}
