package ir.digiexpress.translation;

/**
 * This class includes static utility methods for any functionalities happening inside
 * {@link ir.digiexpress.translation} package
 */
public class Translations {
    /**
     * Just to hide the implicit constructor of this class
     */
    private Translations() {
    }

    /**
     * Quickly create an {@link TranslatableMessage}
     *
     * @param key the key of the translatable
     * @return an express translatable constructed from that key
     */
    public static TranslatableMessage express(final String key) {
        return new TranslatableMessage(key, null);
    }
}
