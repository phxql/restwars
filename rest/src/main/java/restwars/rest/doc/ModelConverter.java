package restwars.rest.doc;

import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.converter.OverrideConverter;
import restwars.rest.resources.param.LocationParam;

/**
 * Helper class to map JAXRS parameters in Swagger.
 */
public final class ModelConverter {
    private ModelConverter() {
    }

    /**
     * Registers the converters with Swagger.
     */
    public static void register() {
        OverrideConverter overrideConverter = new OverrideConverter();

        overrideConverter.add(LocationParam.class.getName(), createJsonString("location"));
        ModelConverters.addConverter(overrideConverter, true);
    }

    private static String createJsonString(String id) {
        return String.format("{\"id\":\"%s\" }", id);
    }
}
