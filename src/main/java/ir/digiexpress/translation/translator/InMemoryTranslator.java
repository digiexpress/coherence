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
    private final Map<Pair<String, Locale>, String> messageTemplateMapping;

    public InMemoryTranslator(final MessageResolver resolver,
                              final Map<Pair<String, Locale>, String> messageTemplateMapping) {
        this.resolver = resolver;
        this.messageTemplateMapping = Objects.requireNonNullElseGet(messageTemplateMapping, HashMap::new);
    }

    @Override
    public String lookup(String key, Locale locale) {
        return Objects.requireNonNullElse(this.messageTemplateMapping.get(new ImmutablePair<>(key, locale)), "");
    }

    @Override
    public MessageResolver getResolver() {
        return this.resolver;
    }
}
