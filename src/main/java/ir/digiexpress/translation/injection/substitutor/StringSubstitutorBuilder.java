package ir.digiexpress.translation.injection.substitutor;

import ir.digiexpress.utils.Assertions;

/**
 * A builder class for {@link StringSubstitutorOptions}.
 * This class also ensures the validity of the config variables.
 */
public class StringSubstitutorBuilder {
    private String parameterPrefix = "{";
    private String parameterSuffix = "}";
    private String defaultValueDelimiter = ":";
    private char escapeCharacter = '\\';
    private boolean preserveEscapes = true;

    StringSubstitutorBuilder() {
    }

    public StringSubstitutorBuilder withPrefix(final String prefix) {
        Assertions.notEmpty(prefix, "prefix");
        this.parameterPrefix = prefix;
        return this;
    }

    public StringSubstitutorBuilder withSuffix(final String suffix) {
        Assertions.notEmpty(suffix, "suffix");
        this.parameterSuffix = suffix;
        return this;
    }

    public StringSubstitutorBuilder withDefaultValueDelimiter(final String delimiter) {
        Assertions.notEmpty(delimiter, "delimiter");
        this.defaultValueDelimiter = delimiter;
        return this;
    }

    public StringSubstitutorBuilder withEscapeChar(final char escapeChar) {
        this.escapeCharacter = escapeChar;
        return this;
    }

    public StringSubstitutorBuilder preserveEscapes() {
        this.preserveEscapes = true;
        return this;
    }

    public StringSubstitutorBuilder doNotPreserveEscapes() {
        this.preserveEscapes = false;
        return this;
    }

    public StringSubstitutorOptions build() {
        return new StringSubstitutorOptions(
                this.parameterPrefix,
                this.parameterSuffix,
                this.defaultValueDelimiter,
                this.escapeCharacter,
                this.preserveEscapes
        );
    }
}
