package ir.digiexpress.translation;


import java.util.Locale;

/**
 * This interface would be responsible for translating a {@link Translatable}
 * to its translated string form in a certain locale
 */
public interface Translator {
    /**
     * Try to translate the translatable to its string form in a certain locale
     *
     * @param translatable the translatable to be translated
     * @param locale       the locale in which we want the translation to happen
     * @return the translated string form of the translatable
     * @see Translatable
     */
    String translate(Translatable translatable, Locale locale);
}
