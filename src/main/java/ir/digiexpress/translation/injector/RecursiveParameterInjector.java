package ir.digiexpress.translation.injector;

import ir.digiexpress.utils.Assertions;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;
import java.util.Objects;

/**
 * Production ready implementation of {@link ParameterInjector}.
 * It recursively substitutes the keys for parameter injection.
 * No caching or optimization is done whatsoever.
 * This class exploits the {@link StringSubstitutor} to substitute parameters as needed.
 */
public class RecursiveParameterInjector implements ParameterInjector {
    public static final String DEFAULT_PARAMETER_PREFIX = "{";
    public static final String DEFAULT_PARAMETER_SUFFIX = "}";
    public static final String DEFAULT_DEFAULT_VALUE_DELIMITER = ":-";
    public static final char DEFAULT_ESCAPE_CHAR = '\\';

    /**
     * <h>A parameter should be declared inside message template for injection.</h>
     *
     * <ul>There are 2 cases of parameter declaration formatting:
     *
     *      <li>#1 no default values formatting : prefix + paramKey + suffix:
     *      <p>example: {paramKey}</p>
     *      <p>in this case the default value of the parameter will be empty.</p>
     *      <p>implementation of {@link ParameterInjector#declareParameter(String)}</p>
     *      </li>
     *
     *      <li>#2 with default values formatting : prefix + paramKey + defaultDelimiter + defaultValue + suffix
     *      <p>example: {paramKey:-defaultValue}</p>
     *      <p>in this case the default value of the parameter will be empty.</p>
     *      <p>implementation of {@link ParameterInjector#declareParameter(String, String)}</p>
     *      </li>
     *
     * </ul>
     */
    private final String parameterPrefix;
    private final String parameterSuffix;
    private final String defaultValueDelimiter;
    private final char escapeCharacter;
    private final String parameterFormatNoDefaultValue;
    private final String parameterFormatWithDefaultValue;

    /**
     * The all args constructor setting up all necessary config
     *
     * @param parameterPrefix       the prefix before parameter declaration in message template
     * @param parameterSuffix       the suffix after parameter declaration in message template
     * @param defaultValueDelimiter the delimiter that separates parameter key from the default value
     * @throws IllegalArgumentException if any of the passed parameters are null or empty
     */
    public RecursiveParameterInjector(final String parameterPrefix,
                                      final String parameterSuffix,
                                      final String defaultValueDelimiter,
                                      final char escapeCharacter) throws IllegalArgumentException {
        Assertions.notEmpty(parameterPrefix, "parameterPrefix");
        Assertions.notEmpty(parameterSuffix, "parameterSuffix");
        Assertions.notEmpty(defaultValueDelimiter, "defaultValueDelimiter");
        this.parameterPrefix = parameterPrefix;
        this.parameterSuffix = parameterSuffix;
        this.defaultValueDelimiter = defaultValueDelimiter;
        this.escapeCharacter = escapeCharacter;
        this.parameterFormatNoDefaultValue = parameterPrefix + "%s" + parameterSuffix;
        this.parameterFormatWithDefaultValue = parameterPrefix + "%s" + defaultValueDelimiter + "%s" + parameterSuffix;
    }

    /**
     * Construct an instance using default settings
     */
    public RecursiveParameterInjector() {
        this(
                DEFAULT_PARAMETER_PREFIX,
                DEFAULT_PARAMETER_SUFFIX,
                DEFAULT_DEFAULT_VALUE_DELIMITER,
                DEFAULT_ESCAPE_CHAR
        );
    }

    @Override
    public String declareParameter(final String paramKey, final String defaultValue) throws IllegalArgumentException {
        Assertions.notEmpty(paramKey, "paramKey");
        return String.format(this.parameterFormatWithDefaultValue, paramKey,
                Objects.requireNonNullElse(defaultValue, ""));
    }

    @Override
    public String declareParameter(final String paramKey) throws IllegalArgumentException {
        Assertions.notEmpty(paramKey, "paramKey");
        return String.format(this.parameterFormatNoDefaultValue, paramKey);
    }

    @Override
    public String inject(final String messageTemplate, final Map<String, Object> parameterMapping) {
        return this.buildParameterSubstitutor(parameterMapping).replace(messageTemplate);
    }

    private StringSubstitutor buildParameterSubstitutor(final Map<String, Object> parameters) {
        return new StringSubstitutor(parameters)
                .setVariablePrefix(this.parameterPrefix)
                .setVariableSuffix(this.parameterSuffix)
                .setValueDelimiter(this.defaultValueDelimiter)
                .setEscapeChar(this.escapeCharacter)
                .setPreserveEscapes(true)
                .setEnableSubstitutionInVariables(true)
                .setDisableSubstitutionInValues(false);
    }
}
