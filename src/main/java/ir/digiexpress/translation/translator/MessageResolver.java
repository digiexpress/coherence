package ir.digiexpress.translation.translator;

import java.util.Map;

/**
 * The interface for resolving messages from a message template and parameter mapping.
 * Parameter injection will happen inside this class.
 */
public interface MessageResolver {
    /**
     * The main responsibility of this interface
     * is to resolve a message from its message template and parameter mapping
     *
     * @param messageTemplate  the template of the message
     * @param parameterMapping mapping of parameters to inject inside the message template
     * @return the resolved message that has the parameters injected into it
     */
    String resolve(final String messageTemplate, final Map<String, Object> parameterMapping);

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
     * Unlike {@link MessageResolver#declareParameter(String, String)}
     * it does not inject a default value for the parameter
     *
     * @param paramKey the key of the parameter
     * @return the declarative form of the parameter
     */
    String declareParameter(final String paramKey);
}
