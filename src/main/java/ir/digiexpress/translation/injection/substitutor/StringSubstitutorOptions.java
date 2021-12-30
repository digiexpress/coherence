package ir.digiexpress.translation.injection.substitutor;

import ir.digiexpress.utils.Assertions;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * This is an immutable config class for {@link StringSubstitutorAdapter}
 * which contains all the options necessary.
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
     * @throws IllegalArgumentException if any of the given parameters are null or empty
     *                                  or if the any two of the prefix, suffix, delimiter and escape character
     *                                  have the same values.
     */
    StringSubstitutorOptions(final String parameterPrefix,
                             final String parameterSuffix,
                             final String defaultValueDelimiter,
                             final char escapeCharacter,
                             final boolean preserveEscapes) throws IllegalArgumentException {
        this.validateInputs(parameterPrefix, parameterSuffix, defaultValueDelimiter, escapeCharacter);
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
    public boolean equals(final Object o) {
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

    private void validateInputs(final String parameterPrefix,
                                final String parameterSuffix,
                                final String defaultValueDelimiter,
                                final char escapeCharacter) throws IllegalArgumentException {
        Assertions.notEmpty(parameterPrefix, "parameterPrefix");
        Assertions.notEmpty(parameterSuffix, "parameterSuffix");
        Assertions.notEmpty(defaultValueDelimiter, "defaultValueDelimiter");

        var uniqueValues = Stream
                .of(parameterPrefix, parameterSuffix, defaultValueDelimiter, "" + escapeCharacter)
                .distinct()
                .count();
        if (uniqueValues < 4)
            throw new IllegalArgumentException("none of the prefix, suffix, delimiter and escape character values must be unique");
    }
}
