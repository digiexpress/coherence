package ir.digiexpress.translation.template;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

/**
 * A template source integrated with springframework built-in message source.
 * This class only delegates the responsibility of retrieving message templates from the MessageSource.
 *
 * @see MessageSource
 */
public class SpringTemplateSource implements MessageTemplateSource {
    private final MessageSource messageSource;

    public SpringTemplateSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String lookup(final String key, final Locale locale) {
        return Objects.requireNonNullElse(this.messageSource.getMessage(key, null, locale), "");
    }
}
