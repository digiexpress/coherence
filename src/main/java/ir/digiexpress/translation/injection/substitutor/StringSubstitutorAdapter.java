package ir.digiexpress.translation.injection.substitutor;

import ir.digiexpress.translation.injection.ParameterInjector;
import ir.digiexpress.utils.Assertions;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;
import java.util.Objects;

/**
 * Production ready implementation of {@link ParameterInjector}.
 * This class exploits the {@link StringSubstitutor} to substitute parameters as needed.
 * And it is thread-safe.
 */
public final class StringSubstitutorAdapter implements ParameterInjector {
    private final StringSubstitutorOptions options;
    private final String parameterFormatNoDefaultValue;
    private final String parameterFormatWithDefaultValue;

    /**
     * Constructs a new instance with given configuration.
     *
     * @param options options of the construction
     */
    public StringSubstitutorAdapter(final StringSubstitutorOptions options) {
        this.options = options;
        this.parameterFormatNoDefaultValue = options.getParameterFormat(false);
        this.parameterFormatWithDefaultValue = options.getParameterFormat(true);
    }

    /**
     * Quickly constructs a {@link StringSubstitutorBuilder} instance
     *
     * @return builder instance with default parameters set on it.
     */
    public static StringSubstitutorBuilder options() {
        return new StringSubstitutorBuilder();
    }

    /**
     * This method declares parameters with default values.
     * Formatting: prefix + paramKey + defaultDelimiter + defaultValue + suffix.
     *
     * @param paramKey     the key of the parameter
     * @param defaultValue the default value for the parameter
     * @return a declared parameter in the configured formatting. e.g. {paramKey:defaultValue}
     * @throws IllegalArgumentException if the paramKey is null or empty
     * @see StringSubstitutorAdapter#declareParameter(String)
     */
    @Override
    public String declareParameter(final String paramKey, final String defaultValue) throws IllegalArgumentException {
        Assertions.notEmpty(paramKey, "paramKey");
        return String.format(
                this.parameterFormatWithDefaultValue,
                paramKey,
                Objects.requireNonNullElse(defaultValue, "")
        );
    }

    /**
     * This method  declares parameters with no default values.
     * The default value would be empty in this case.
     * Formatting: prefix + paramKey + suffix
     *
     * @param paramKey the key of the parameter
     * @return a declared parameter in the configured formatting. e.g. {paramKey}
     * @throws IllegalArgumentException if the paramKey is null
     * @see StringSubstitutorAdapter#declareParameter(String, String)
     */
    @Override
    public String declareParameter(final String paramKey) throws IllegalArgumentException {
        Assertions.notEmpty(paramKey, "paramKey");
        return String.format(this.parameterFormatNoDefaultValue, paramKey);
    }

    /**
     * Inject given parameters into the message template.
     * Calling this method causes no side effects on the object.
     *
     * @param messageTemplate  the template of the message
     * @param parameterMapping mapping of parameters to inject inside the message template
     * @return a new string that follows the template and has the parameters injected into it
     */
    @Override
    public String inject(final String messageTemplate, final Map<String, Object> parameterMapping) {
        return this.buildParameterSubstitutor(parameterMapping).replace(messageTemplate);
    }

    private StringSubstitutor buildParameterSubstitutor(final Map<String, Object> parameters) {
        return new StringSubstitutor(parameters)
                .setVariablePrefix(this.options.getParameterPrefix())
                .setVariableSuffix(this.options.getParameterSuffix())
                .setValueDelimiter(this.options.getDefaultValueDelimiter())
                .setEscapeChar(this.options.getEscapeCharacter())
                .setPreserveEscapes(this.options.doesPreserveEscapes())
                .setEnableSubstitutionInVariables(true)
                .setDisableSubstitutionInValues(false);
    }
}
