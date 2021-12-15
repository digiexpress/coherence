package ir.digiexpress.translation;


import com.google.common.base.CaseFormat;

import java.util.Collections;
import java.util.Map;

/**
 * Any translatable "thing" can implement this interface
 * Having a message key and some parameters to inject can lead us to a string form.
 */
public interface Translatable {
    /**
     * Each translatable has a lookup key inside a MessageSource e.g. "translatableLookupKey".
     * This key will be later used by the {@link Translator} to lookup for the message template.
     * You are encouraged to follow the lowerCamelCase naming convention for these keys.
     * To avoid the reflection overload, just implement the method yourself.
     *
     * @return the key to lookup in MessageSource.
     * default implementation gets the key from the class name
     */
    default String getKey() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, this.getClass().getSimpleName());
    }

    /**
     * These are the parameters that will be injected into the {@link Translatable}'s message
     * The translatable itself is aware of these parameters.
     * Note that string values of the parameters will be picked up from {@link Object#toString()} for injection process
     *
     * @return a mapping of parameter keys (String) to parameter values (Object)
     */
    default Map<String, Object> getParameters() {
        return Collections.emptyMap();
    }
}
