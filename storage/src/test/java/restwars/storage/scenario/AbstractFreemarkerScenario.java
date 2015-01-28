package restwars.storage.scenario;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Abstract base class for every scenario which uses Freemarker to generate the SQL.
 *
 * @param <T> Class of the model.
 */
public abstract class AbstractFreemarkerScenario<T> implements Scenario {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFreemarkerScenario.class);

    /**
     * Freemarker configuration.
     */
    private final Configuration configuration;

    /**
     * Constructor.
     */
    public AbstractFreemarkerScenario() {
        configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setClassForTemplateLoading(AbstractFreemarkerScenario.class, "/");
        configuration.setDefaultEncoding("UTF-8");
    }

    @Override
    public void create(Handle handle) throws ScenarioException {
        Template template;
        try {
            template = configuration.getTemplate(getTemplateName());
        } catch (IOException e) {
            throw new ScenarioException("Exception while loading Freemarker template", e);
        }

        StringWriter writer = new StringWriter();
        try {
            template.process(getModel(), writer);
        } catch (TemplateException | IOException e) {
            throw new ScenarioException("Exception while rendering Freemarker template", e);
        }

        String sql = writer.toString();
        LOGGER.trace("Generated SQL: {}", sql);

        handle.execute(sql);
    }

    /**
     * Returns the model.
     *
     * @return Model.
     */
    protected abstract T getModel();

    /**
     * Returns the template name.
     *
     * @return Template name.
     */
    protected abstract String getTemplateName();
}
