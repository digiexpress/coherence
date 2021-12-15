package ir.digiexpress.translation;


import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * Any translatable "thing" can implement this interface
 * Having a message key and some parameters to inject can lead us to a string form in a specified locale.
 */
public interface Translatable extends Serializable {
    /**
     * Each translatable has a lookup key inside a MessageSource e.g. "translatableLookup.key".
     * This key will be later used by the {@link Translator} to lookup for the message template.
     * You are encouraged to follow the lowerCamelCase naming convention for the key.
     *
     * @return the key to be later looked up inside {@link Translator#lookup(String, Locale)}
     */
    String getKey();

    /**
     * These are the parameters that will be injected into the {@link Translatable}'s message
     * The translatable itself is aware of these parameters.
     * Note that string values of the parameters will be picked up from {@link Object#toString()} for injection process
     *
     * @return a mapping of parameter keys (String) to parameter values (Object)
     */
    Map<String, Object> getParameters();
}
