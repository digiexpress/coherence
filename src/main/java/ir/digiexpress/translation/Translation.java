package ir.digiexpress.translation;

import ir.digiexpress.translation.injector.RecursiveParameterInjector;
import ir.digiexpress.translation.translatable.TranslatableMessage;

/**
 * This class includes static utility methods for any functionalities happening inside
 * {@link ir.digiexpress.translation} package
 */
public class Translation {
    public static final String DEFAULT_PARAMETER_PREFIX = "{";
    public static final String DEFAULT_PARAMETER_SUFFIX = "}";
    public static final String DEFAULT_DEFAULT_VALUE_DELIMITER = ":-";

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
     * @see TranslatableMessage
     */
    public static TranslatableMessage message(final String key) {
        return new TranslatableMessage(key, null);
    }

    /**
     * Quickly create a {@link RecursiveParameterInjector} using default settings
     *
     * @return a recursive message resolver constructed from the default settings
     * @see RecursiveParameterInjector
     */
    public static RecursiveParameterInjector defaultMessageResolver() {
        return new RecursiveParameterInjector(
                DEFAULT_PARAMETER_PREFIX,
                DEFAULT_PARAMETER_SUFFIX,
                DEFAULT_DEFAULT_VALUE_DELIMITER
        );
    }
}
