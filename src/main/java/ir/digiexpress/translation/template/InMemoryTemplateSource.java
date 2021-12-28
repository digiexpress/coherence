package ir.digiexpress.translation.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Out of the box implementation of {@link MessageTemplateSource}.
 * This class is ready for production use.
 */
public class InMemoryTemplateSource implements MessageTemplateSource {
    /**
     * Message templates are stored inside this map.
     */
    private final ConcurrentMap<Pair<String, Locale>, String> templateMapping;

    public InMemoryTemplateSource() {
        this.templateMapping = new ConcurrentHashMap<>();
    }

    public InMemoryTemplateSource(final ConcurrentMap<Pair<String, Locale>, String> templateMapping) {
        this.templateMapping = Objects.requireNonNullElseGet(templateMapping, ConcurrentHashMap::new);
    }

    /**
     * Retrieve the message template bound to the given key and locale.
     *
     * @param key    the key too look up
     * @param locale the locale to look up the key inside
     * @return the message template if found, otherwise an empty string
     */
    @Override
    public String lookup(final String key, final Locale locale) {
        return Objects.requireNonNullElse(this.templateMapping.get(new ImmutablePair<>(key, locale)), "");
    }

    /**
     * Puts a message template in its memory
     *
     * @param messageKey      the key of the message template
     * @param locale          the locale of the message template
     * @param messageTemplate the message template itself
     * @throws IllegalArgumentException when messageKey is null or empty, and when locale is null
     */
    public InMemoryTemplateSource put(final String messageKey,
                                      final Locale locale,
                                      final String messageTemplate) throws IllegalArgumentException {
        this.validateMessageKey(messageKey);
        if (locale == null) throw new IllegalArgumentException("locale cannot be null");
        this.templateMapping.put(new ImmutablePair<>(messageKey, locale), messageTemplate);
        return this;
    }

    private void validateMessageKey(final String messageKey) {
        if (messageKey == null) throw new IllegalArgumentException("message key cannot be null");
        if (messageKey.isBlank()) throw new IllegalArgumentException("message key cannot be empty");
    }
}
