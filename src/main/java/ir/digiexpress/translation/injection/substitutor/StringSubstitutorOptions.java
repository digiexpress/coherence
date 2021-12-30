package ir.digiexpress.translation.injection.substitutor;

import ir.digiexpress.utils.Assertions;

import java.util.Objects;

/**
 * This is an immutable config class for {@link StringSubstitutorAdapter} and it contains all the options necessary.
 */
final class StringSubstitutorOptions {
    /**
     * Below fields cannot be null!
     */
    private final String parameterPrefix;
    private final String parameterSuffix;
    private final String defaultValueDelimiter;
    private final char escapeCharacter;
    private final boolean preserveEscapes;

    /**
     * Construct a new instance with given properties.
     *
     * @param parameterPrefix       prefix of the parameters in message template
     * @param parameterSuffix       suffix of the parameter in message template
     * @param defaultValueDelimiter the default value delimiter of parameters in message template
     * @param escapeCharacter       the character used to escape above inputs in the message template
     */
    StringSubstitutorOptions(final String parameterPrefix,
                             final String parameterSuffix,
                             final String defaultValueDelimiter,
                             final char escapeCharacter,
                             final boolean preserveEscapes) throws IllegalArgumentException {
        Assertions.notEmpty(parameterPrefix, "parameterPrefix");
        Assertions.notEmpty(parameterSuffix, "parameterSuffix");
        Assertions.notEmpty(defaultValueDelimiter, "defaultValueDelimiter");
        this.parameterPrefix = parameterPrefix;
        this.parameterSuffix = parameterSuffix;
        this.defaultValueDelimiter = defaultValueDelimiter;
        this.escapeCharacter = escapeCharacter;
        this.preserveEscapes = preserveEscapes;
    }

    String getParameterFormat(final boolean hasDefaultValue) {
        return hasDefaultValue ?
                this.parameterPrefix + "%s" + this.defaultValueDelimiter + "%s" + this.parameterSuffix :
                this.parameterPrefix + "%s" + this.parameterSuffix;
    }

    String getParameterPrefix() {
        return this.parameterPrefix;
    }

    String getParameterSuffix() {
        return this.parameterSuffix;
    }

    String getDefaultValueDelimiter() {
        return this.defaultValueDelimiter;
    }

    char getEscapeCharacter() {
        return this.escapeCharacter;
    }

    public boolean doesPreserveEscapes() {
        return preserveEscapes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSubstitutorOptions that = (StringSubstitutorOptions) o;
        return this.escapeCharacter == that.escapeCharacter &&
                this.preserveEscapes == that.preserveEscapes &&
                this.parameterPrefix.equals(that.parameterPrefix) &&
                this.parameterSuffix.equals(that.parameterSuffix) &&
                this.defaultValueDelimiter.equals(that.defaultValueDelimiter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.parameterPrefix,
                this.parameterSuffix,
                this.defaultValueDelimiter,
                this.escapeCharacter,
                this.preserveEscapes
        );
    }
}
