package ir.digiexpress.translation;

import com.google.common.base.CaseFormat;

import java.util.Collections;
import java.util.Map;

/**
 * Extend this abstract class in domain classes that you want to have translations in different locales.
 * This class simply implements some boilerplate code out of the box for you.
 */
public abstract class TranslatableDomain implements Translatable {
    /**
     * This implementation simple generates the translation key from the class name.
     * It follows the lowerCaseCamel naming convention.
     *
     * @return the key to lookup for translation
     */
    @Override
    public String getKey() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, this.getClass().getSimpleName());
    }

    /**
     * The default parameters for a domain class is just empty.
     * You can override this method for further customization
     *
     * @return an empty parameter mapping
     */
    @Override
    public Map<String, Object> getParameters() {
        return Collections.emptyMap();
    }
}
