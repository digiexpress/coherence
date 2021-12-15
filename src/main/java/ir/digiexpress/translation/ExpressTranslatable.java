package ir.digiexpress.translation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * This is probably the most naive implementation of {@link Translatable}
 * which in the parameters are put in raw form. Note that this class is NOT thread safe.
 */
public class ExpressTranslatable implements Translatable, Serializable {
    protected final String key;
    protected final Map<String, Object> parameters;

    /**
     * Quickly create a normal translatable with parameters
     *
     * @param key        the key of the Translatable message
     * @param parameters the parameters to inject into message template
     * @throws IllegalArgumentException when the key is passed null
     */
    public ExpressTranslatable(final String key, final Map<String, Object> parameters) throws IllegalArgumentException {
        if (Objects.isNull(key))
            throw new IllegalArgumentException("key cannot be null");
        if (key.isBlank())
            throw new IllegalArgumentException("key cannot be empty");
        this.key = key;
        this.parameters = Objects.requireNonNullElseGet(parameters, HashMap::new);
    }

    /**
     * This method will add a parameter key and value to inject inside the message template
     * If the paramKey already exists inside the parameter mapping, the paramValue will override the previous one
     *
     * @param paramKey   key of the parameter inside message template
     * @param paramValue the value of the parameter to inject,
     *                   string form will be later picked up by {@link Object#toString()}
     * @return this for chain effect
     * @throws IllegalArgumentException when the paramKey is passed null
     */
    public ExpressTranslatable put(final String paramKey, final Object paramValue) throws IllegalArgumentException {
        if (Objects.isNull(paramKey))
            throw new IllegalArgumentException("the parameter key cannot be null");
        if (paramKey.isBlank())
            throw new IllegalArgumentException("the parameter key cannot be empty");
        this.parameters.put(paramKey, paramValue);
        return this;
    }

    /**
     * This method will add a parameter key and value to inject inside the message template.
     * You can use this method to lazily fetch a value if the parameter key does not exist in mapping.
     * A particular use case would be to fill in the unfilled parameters.
     *
     * @param paramKey        key of the parameter inside message template
     * @param mappingFunction the supplier for parameter value, will be lazily called later
     *                        if the paramKey doesn't exist in the mapping
     * @return this for chain effect
     * @throws IllegalArgumentException when the paramKey is passed null
     */
    public ExpressTranslatable putIfNotExists(final String paramKey,
                                              final Function<String, Object> mappingFunction) {
        if (Objects.isNull(paramKey))
            throw new IllegalArgumentException("the parameter key cannot be null");
        if (paramKey.isBlank())
            throw new IllegalArgumentException("the parameter key cannot be empty");
        if (Objects.isNull(mappingFunction))
            throw new IllegalArgumentException("the parameter supplier cannot be null");
        this.parameters.computeIfAbsent(paramKey, mappingFunction);
        return this;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder(String.format("Translatable(key=%s", this.key));
        if (this.parameters != null && !this.parameters.isEmpty())
            builder.append(String.format(", parameters=%s", this.parameters));
        return builder.append(')').toString();
    }
}
