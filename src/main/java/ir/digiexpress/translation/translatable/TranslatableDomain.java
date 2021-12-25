package ir.digiexpress.translation.translatable;

import com.google.common.base.CaseFormat;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Extend this abstract class in domain classes that you want to have translations in different locales.
 * It implements some boilerplate code out of the box for you.
 */
public abstract class TranslatableDomain implements Translatable {
    /**
     * the cache for generated key of the domain class
     */
    private String cachedKey = null;

    /**
     * This implementation simply generates the translation key from the class name.
     * It follows the lowerCaseCamel naming convention.
     *
     * @return the key to lookup for translation
     */
    @Override
    public String getKey() {
        if (Objects.isNull(this.cachedKey))
            this.cachedKey = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, this.getClass().getSimpleName());
        return this.cachedKey;
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
