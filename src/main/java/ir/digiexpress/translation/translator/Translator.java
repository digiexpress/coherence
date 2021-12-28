package ir.digiexpress.translation.translator;


import ir.digiexpress.translation.translatable.Translatable;

import java.util.Locale;

/**
 * This interface would be responsible for translating a {@link Translatable}
 * to its translated string form in a certain locale
 */
public interface Translator {
    /**
     * Try to translate the translatable to its string form in a certain locale.
     * The default implementation exploits the message resolver and
     *
     * @param translatable the translatable to be translated
     * @param locale       the locale in which we want the translation to happen
     * @return the translated string form of the translatable.
     * if the translation is not possible, the output will be an empty string
     * @see Translatable
     */
    String translate(final Translatable translatable, final Locale locale);
}
