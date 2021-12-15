package ir.digiexpress.translation;

import ir.digiexpress.translation.translatable.TranslatableMessage;

/**
 * This class includes static utility methods for any functionalities happening inside
 * {@link ir.digiexpress.translation} package
 */
public class Translation {
    /**
     * Just to hide the implicit constructor of this class
     */
    private Translation() {
    }

    /**
     * Quickly create a {@link TranslatableMessage}
     *
     * @param key the key of the translatable
     * @return a translatable message constructed from that key
     */
    public static TranslatableMessage message(final String key) {
        return new TranslatableMessage(key, null);
    }
}
