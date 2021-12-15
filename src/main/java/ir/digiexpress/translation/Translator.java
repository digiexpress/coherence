package ir.digiexpress.translation;


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
     * Try to translate the translatable to its string form in a certain locale
     *
     * @param translatable the translatable to be translated
     * @param locale       the locale in which we want the translation to happen
     * @return the translated string form of the translatable
     * @see Translatable
     */
    String translate(final Translatable translatable, final Locale locale);
}
