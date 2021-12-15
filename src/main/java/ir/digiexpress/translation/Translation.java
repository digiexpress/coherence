package ir.digiexpress.translation;

/**
 * This class includes some static methods as a shortcut for translation construction
 */
public class Translation {
    /**
     * Just to disable the construction of this class
     */
    private Translation() {
    }

    /**
     * Quickly create an {@link ExpressTranslatable}
     *
     * @param key the key of the translatable
     * @return an express translatable constructed from that key
     */
    public static ExpressTranslatable express(final String key) {
        return new ExpressTranslatable(key, null);
    }
}
