package ir.digiexpress.translation.translator;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Out of the box implementation of {@link Translator} ready for production use.
 * All the message templates are held in memory within this class.
 */
public class InMemoryTranslator implements Translator {
    private final MessageResolver resolver;
    /**
     * Message templates are stored inside this map.
     */
    private final Map<Pair<String, Locale>, String> messageTemplateMapping;

    /**
     * All args constructor for an in memory translator
     *
     * @param resolver               the message resolver that handles parameter injection
     * @param messageTemplateMapping the mapping of the message templates
     * @throws IllegalArgumentException when resolver is passed null
     */
    public InMemoryTranslator(final MessageResolver resolver,
                              final Map<Pair<String, Locale>, String> messageTemplateMapping) throws IllegalArgumentException {
        if (resolver == null) throw new IllegalArgumentException("resolver cannot be null");
        this.resolver = resolver;
        this.messageTemplateMapping = Objects.requireNonNullElseGet(messageTemplateMapping, HashMap::new);
    }

    /**
     * Create an in memory translator from only a resolver
     *
     * @param resolver the message resolver for translator that will later be used for parameter injection
     */
    public InMemoryTranslator(final MessageResolver resolver) {
        this(resolver, null);
    }

    /**
     * Looks up a message key in a certain locale
     *
     * @param key    the key too look up
     * @param locale the locale to look up the key inside
     * @return the message template if found, otherwise an empty string
     */
    @Override
    public String lookup(String key, Locale locale) {
        return Objects.requireNonNullElse(this.messageTemplateMapping.get(new ImmutablePair<>(key, locale)), "");
    }

    /**
     * Puts a message template in its memory
     *
     * @param messageKey      the key of the message template
     * @param locale          the locale of the message template
     * @param messageTemplate the message template itself
     * @throws IllegalArgumentException when messageKey is null or empty, and when locale is null
     */
    public void put(final String messageKey,
                    final Locale locale,
                    final String messageTemplate) throws IllegalArgumentException {
        this.validateMessageKey(messageKey);
        if (locale == null) throw new IllegalArgumentException("locale cannot be null");
        this.messageTemplateMapping.put(new ImmutablePair<>(messageKey, locale), messageTemplate);
    }

    private void validateMessageKey(final String messageKey) {
        if (messageKey == null) throw new IllegalArgumentException("message key cannot be null");
        if (messageKey.isBlank()) throw new IllegalArgumentException("message key cannot be empty");
    }

    @Override
    public MessageResolver getResolver() {
        return this.resolver;
    }
}
