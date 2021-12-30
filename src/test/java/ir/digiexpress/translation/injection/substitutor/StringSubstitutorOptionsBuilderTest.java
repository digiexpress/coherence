package ir.digiexpress.translation.injection.substitutor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringSubstitutorOptionsBuilderTest {
    private StringSubstitutorOptionsBuilder builder;

    @BeforeEach
    void setup() {
        this.builder = new StringSubstitutorOptionsBuilder()
                .doNotPreserveEscapes()
                .preserveEscapes()
                .withDefaultValueDelimiter(":")
                .withPrefix("{")
                .withSuffix("}")
                .withEscapeChar('\\');
    }

    @Test
    void testWithPrefix_whenPassedNullOrEmpty_thenThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> builder = builder.withPrefix(null));
        assertThrows(IllegalArgumentException.class, () -> builder = builder.withPrefix(""));
    }

    @Test
    void testWithSuffix_whenPassedNullOrEmpty_thenThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> builder = builder.withSuffix(null));
        assertThrows(IllegalArgumentException.class, () -> builder = builder.withSuffix(""));
    }

    @Test
    void testWithDefaultValueParameter_whenPassedNullOrEmpty_thenThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> builder = builder.withDefaultValueDelimiter(null));
        assertThrows(IllegalArgumentException.class, () -> builder = builder.withDefaultValueDelimiter(""));
    }

    @Test
    void testBuild_whenCalled_thenEveryOptionIsSetProperly() {
        var expectedOptions = new StringSubstitutorOptions("{", "}", ":", '\\', true);
        var gotOptions = this.builder.build();

        assertEquals(expectedOptions, gotOptions);
    }
}
