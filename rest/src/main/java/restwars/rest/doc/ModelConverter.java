package restwars.rest.doc;

import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.converter.OverrideConverter;
import restwars.rest.resources.param.LocationParam;

public final class ModelConverter {
    private ModelConverter() {
    }

    public static void register() {
        OverrideConverter overrideConverter = new OverrideConverter();

        overrideConverter.add(LocationParam.class.getName(), createJsonString("location"));
        ModelConverters.addConverter(overrideConverter, true);
    }

    private static String createJsonString(String id) {
        return String.format("{\"id\":\"%s\" }", id);
    }
}
