package ir.digiexpress.translation.translator;

import ir.digiexpress.translation.Translation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTranslatorTest {
    private InMemoryTranslator translator;
    private final String message1 = "{k1} {k2} {k3:-default}";
    private final String message2 = "{k4} {k5} {k6:-default}";

    @BeforeEach
    void setUp() {
        // create mock message resolver
        var resolver = Mockito.mock(MessageResolver.class);
        Mockito.when(resolver.resolve(this.message1, Map.of("k1", "v1", "k2", "v2")))
                .thenReturn("v1 v2 default");
        Mockito.when(resolver.resolve(this.message2, Map.of("k4", "v4", "k5", "v5")))
                .thenReturn("v4 v5 default");

        // construct an in memory translator
        this.translator = new InMemoryTranslator(
                resolver,
                Map.of(
                        new ImmutablePair<>("message1", Locale.ENGLISH), this.message1,
                        new ImmutablePair<>("message2", Locale.FRENCH), this.message2
                )
        );
    }

    @Test
    void testLookup_whenPassedExistingEntries_thenReturnsOk() {
        assertEquals(this.message1, this.translator.lookup("message1", Locale.ENGLISH));
        assertEquals(this.message2, this.translator.lookup("message2", Locale.FRENCH));
    }

    @Test
    void testLookup_whenNonExistentEntries_thenReturnsEmpty() {
        assertTrue(this.translator.lookup("aMessage", Locale.ENGLISH).isBlank());
    }

    @Test
    void testTranslate_whenPassedParameters_thenWorksOk() {
        var translatable = Translation.message("message1")
                .put("k1", "v1")
                .put("k2", "v2");
        var expected = "v1 v2 default";
        assertEquals(expected, this.translator.translate(translatable, Locale.ENGLISH));
    }
}