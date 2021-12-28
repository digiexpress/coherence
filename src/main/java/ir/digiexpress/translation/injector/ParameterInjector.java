package ir.digiexpress.translation.injector;

import java.util.Map;

/**
 * The interface for injecting parameters inside messages templates.
 */
public interface ParameterInjector {
    /**
     * Inject a parameter mapping into a given message template.
     *
     * @param messageTemplate  the template of the message
     * @param parameterMapping mapping of parameters to inject inside the message template
     * @return the result message that has the parameters injected into it
     */
    String inject(final String messageTemplate, final Map<String, Object> parameterMapping);

    /**
     * This method returns a declarative string form of a parameter e.g. {@code "{paramKey:-defaultValue}"}
     *
     * @param paramKey     the key of the parameter
     * @param defaultValue the default value for the parameter
     * @return the declarative form of the parameter
     */
    String declareParameter(final String paramKey, final String defaultValue);

    /**
     * This method returns a declarative string form of a parameter e.g. {@code "{paramKey}"}.
     * Unlike {@link ParameterInjector#declareParameter(String, String)}
     * it does not inject a default value for the parameter
     *
     * @param paramKey the key of the parameter
     * @return the declarative form of the parameter
     */
    String declareParameter(final String paramKey);
}
