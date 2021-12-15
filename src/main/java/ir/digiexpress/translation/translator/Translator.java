package ir.digiexpress.translation.translator;


import ir.digiexpress.translation.translatable.Translatable;

import java.util.Locale;

/**
 * This interface would be responsible for translating a {@link Translatable}
 * to its translated string form in a certain locale
 */
public interface Translator {
    /**
     * Each translator must come with a message template repository that keys and locale can be looked up in it.
     *
     * @param key    the key too look up
     * @param locale the locale to look up the key inside
     * @return the message template bound to the key in specified locale
     */
    String lookup(final String key, final Locale locale);

    /**
     * Try to translate the translatable to its string form in a certain locale.
     * The default implementation exploits the message resolver and
     *
     * @param translatable the translatable to be translated
     * @param locale       the locale in which we want the translation to happen
     * @return the translated string form of the translatable
     * @see Translatable
     */
    default String translate(final Translatable translatable, final Locale locale) {
        var template = this.lookup(translatable.getKey(), locale);
        return this.getResolver().resolve(template, translatable.getParameters());
    }

    /**
     * Each translator has a message resolver that does the parameter injection for it
     *
     * @return the resolver of the translator
     */
    MessageResolver getResolver();
}
