package ir.digiexpress.translation.template;

import ir.digiexpress.translation.injector.ParameterInjector;

import java.util.Locale;

/**
 * This is a source / repository used to lookup message templates.
 * The output message templates will be later used by {@link ParameterInjector}
 * for the injection process
 */
public interface MessageTemplateSource {
    /**
     * Each translator must come with a message template repository that keys and locale can be looked up in it.
     *
     * @param key    key of the message template to look up
     * @param locale the locale to look up the key inside
     * @return the message template bound to the key in specified locale
     */
    String lookup(final String key, final Locale locale);
}
