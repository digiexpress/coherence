package ir.digiexpress.translation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is for the messages of the domain, it carries a key to a message template and some context within it.
 * Note that this class is NOT thread safe.
 */
public class TranslatableMessage implements Translatable {
    /**
     * The key to the message template is final and is set upon construction.
     * This field cannot be null.
     */
    protected final String key;
    /**
     * Contexts are held in raw form and in a mapping of String to Object.
     * This field cannot be null.
     */
    protected final Map<String, Object> parameters;

    /**
     * Create a translatable message with a key and no parameters
     *
     * @param key the key of the translatable message
     * @throws IllegalArgumentException when the key is passed null or empty
     */
    public TranslatableMessage(final String key) throws IllegalArgumentException {
        this(key, null);
    }

    /**
     * Create a translatable message with parameters
     *
     * @param key        the key of the translatable message
     * @param parameters the parameters to inject into message template
     * @throws IllegalArgumentException when the key is passed null or empty
     */
    public TranslatableMessage(final String key, final Map<String, Object> parameters) throws IllegalArgumentException {
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
    public TranslatableMessage put(final String paramKey, final Object paramValue) throws IllegalArgumentException {
        this.validateParameterKey(paramKey);
        this.parameters.put(paramKey, paramValue);
        return this;
    }

    /**
     * You can use this method to lazily fetch a value if the parameter key does not exist in mapping.
     * A particular use case would be to fill in the unfilled parameters.
     *
     * @param paramKey key of the parameter inside message template
     * @param mapping  the mapping from param key to param value, will be lazily called later
     *                 if the param key doesn't exist in the mapping
     * @return this for chain effect
     * @throws IllegalArgumentException when the paramKey is passed null or is empty,
     *                                  and when the mapping function is null
     */
    public TranslatableMessage putIfAbsent(final String paramKey,
                                           final Function<String, Object> mapping) {
        this.validateParameterKey(paramKey);
        if (Objects.isNull(mapping))
            throw new IllegalArgumentException("the parameter mapping cannot be null");
        this.parameters.computeIfAbsent(paramKey, mapping);
        return this;
    }

    /**
     * You can use this method to lazily fetch a value if the parameter key does not exist in mapping.
     * A particular use case would be to fill in the unfilled parameters.
     *
     * @param paramKey key of the parameter inside message template
     * @param supplier the supplier for parameter value, will be lazily called later
     *                 if the paramKey doesn't exist in the mapping
     * @return this for chain effect
     * @throws IllegalArgumentException when the paramKey is passed null or is empty,
     *                                  and when the supplier is null
     */
    public TranslatableMessage putIfAbsent(final String paramKey,
                                           final Supplier<Object> supplier) {
        this.putIfAbsent(paramKey, s -> supplier.get());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslatableMessage that = (TranslatableMessage) o;
        return key.equals(that.key) && parameters.equals(that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, parameters);
    }

    protected final void validateParameterKey(final String paramKey) {
        if (Objects.isNull(paramKey))
            throw new IllegalArgumentException("the parameter key cannot be null");
        if (paramKey.isBlank())
            throw new IllegalArgumentException("the parameter key cannot be empty");
    }
}
