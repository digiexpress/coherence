package ir.digiexpress.translation.translator;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;
import java.util.Objects;

/**
 * Production ready implementation of {@link MessageResolver}.
 * It recursively substitutes the keys for parameter injection.
 */
public class RecursiveMessageResolver implements MessageResolver {
    /**
     * A parameter should be declared inside message template for injection.
     * As an example {paramKey:-defaultValue} is a parameter declaration with the following specs:
     * {@code parameterPrefix="{", parameterSuffix="}", defaultValueDelimiter=":-"}
     */
    private final String parameterPrefix;
    private final String parameterSuffix;
    private final String defaultValueDelimiter;
    private final String parameterFormatNoDefaultValue;
    private final String parameterFormatWithDefaultValue;

    /**
     * The all args constructor for an in memory message resolver
     *
     * @param parameterPrefix       the prefix before parameter declaration in message template
     * @param parameterSuffix       the suffix after parameter declaration in message template
     * @param defaultValueDelimiter the delimiter that separates parameter key from the default value
     * @throws IllegalArgumentException if any of the passed parameters are null or empty
     */
    public RecursiveMessageResolver(final String parameterPrefix,
                                    final String parameterSuffix,
                                    final String defaultValueDelimiter) throws IllegalArgumentException {
        this.parameterPrefix = parameterPrefix;
        this.parameterSuffix = parameterSuffix;
        this.defaultValueDelimiter = defaultValueDelimiter;
        this.parameterFormatNoDefaultValue = parameterPrefix + "%s" + parameterSuffix;
        this.parameterFormatWithDefaultValue = parameterPrefix + "%s" + defaultValueDelimiter + "%s" + parameterSuffix;
    }

    @Override
    public String declareParameter(final String paramKey, final String defaultValue) {
        this.validateParamKey(paramKey);
        return String.format(this.parameterFormatWithDefaultValue, paramKey,
                Objects.requireNonNullElse(defaultValue, ""));
    }

    @Override
    public String declareParameter(final String paramKey) {
        this.validateParamKey(paramKey);
        return String.format(this.parameterFormatNoDefaultValue, paramKey);
    }

    @Override
    public String resolve(final String messageTemplate, final Map<String, Object> parameterMapping) {
        return this.buildParameterSubstitutor(parameterMapping).replace(messageTemplate);
    }

    private StringSubstitutor buildParameterSubstitutor(final Map<String, Object> parameters) {
        return new StringSubstitutor(parameters)
                .setVariablePrefix(this.parameterPrefix)
                .setVariableSuffix(this.parameterSuffix)
                .setValueDelimiter(this.defaultValueDelimiter)
                .setPreserveEscapes(true)
                .setEnableSubstitutionInVariables(true)
                .setDisableSubstitutionInValues(false);
    }

    private void validateParamKey(final String paramKey) {
        if (Objects.isNull(paramKey)) throw new IllegalArgumentException("parameter key cannot be null");
        if (paramKey.isBlank()) throw new IllegalArgumentException("parameter key cannot be empty");
    }
}
