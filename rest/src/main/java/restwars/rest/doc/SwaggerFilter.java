package restwars.rest.doc;

import com.wordnik.swagger.core.filter.SwaggerSpecFilter;
import com.wordnik.swagger.model.ApiDescription;
import com.wordnik.swagger.model.Operation;
import com.wordnik.swagger.model.Parameter;

import java.util.List;
import java.util.Map;

/**
 * Filter class to remove all JAXRS parameters annotated with access="internal". Otherwise the @Auth annotated parameters
 * from Dropwizard confuse Swagger.
 */
public class SwaggerFilter implements SwaggerSpecFilter {
    @Override
    public boolean isOperationAllowed(Operation operation, ApiDescription apiDescription, Map<String, List<String>> map, Map<String, String> map1, Map<String, List<String>> map2) {
        return true;
    }

    @Override
    public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription apiDescription, Map<String, List<String>> map, Map<String, String> map1, Map<String, List<String>> map2) {
        return !(parameter.paramAccess().isDefined() && parameter.paramAccess().get().equals("internal"));
    }
}
