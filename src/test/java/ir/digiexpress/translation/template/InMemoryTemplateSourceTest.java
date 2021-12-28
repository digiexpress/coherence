package ir.digiexpress.translation.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTemplateSourceTest {
    private final String message1Key = "message1";
    private final String message2Key = "message2";
    private final String message1Template = "{k1} {k2} {k3:-default}";
    private final String message2Template = "{k4} {k5} {k6:-default}";
    private final Locale message1Locale = Locale.ENGLISH;
    private final Locale message2Locale = Locale.FRENCH;
    private InMemoryTemplateSource templateSource;

    @BeforeEach
    void setUp() {
        // construct an in memory translator
        this.templateSource = new InMemoryTemplateSource(
                new ConcurrentHashMap<>() {{
                    put(new ImmutablePair<>(message1Key, message1Locale), message1Template);
                    put(new ImmutablePair<>(message2Key, message2Locale), message2Template);
                }}
        );
    }

    @Test
    void testEmptyConstructor_whenNoArguments_thenMapIsInitialized() {
        var source = new InMemoryTemplateSource();
        var newKey = "aMessageKey";
        var newTemplate = "aTemplate";

        // assert key doesn't exist
        assertTrue(source.lookup(newKey, Locale.ENGLISH).isBlank());
        source = source.put(newKey, Locale.ENGLISH, newTemplate);

        // assert key exists after the insertion
        assertEquals(newTemplate, source.lookup(newKey, Locale.ENGLISH));
    }

    @Test
    void testPut_whenPassedNullOrEmptyKey_thenThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> this.templateSource.put(null, Locale.ENGLISH, "aTemplate"));
        assertThrows(IllegalArgumentException.class, () -> this.templateSource.put("", Locale.ENGLISH, "aTemplate"));
    }

    @Test
    void testPut_whenPassedNullLocale_thenThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> this.templateSource.put("aKey", null, "aTemplate"));
    }

    @Test
    void testPut_whenPassedOkEntries_thenAddsToSource() {
        var newKey = "aMessageKey";
        var expectedTemplate = "aTemplate";

        // assert key doesn't exist
        assertTrue(this.templateSource.lookup(newKey, Locale.ENGLISH).isBlank());
        this.templateSource = this.templateSource.put(newKey, Locale.ENGLISH, expectedTemplate);

        // assert key exists after the insertion
        assertEquals(expectedTemplate, this.templateSource.lookup(newKey, Locale.ENGLISH));
    }

    @Test
    void testLookup_whenPassedExistingEntries_thenReturnsTemplate() {
        assertEquals(this.message1Template, this.templateSource.lookup(message1Key, this.message1Locale));
        assertEquals(this.message2Template, this.templateSource.lookup(message2Key, this.message2Locale));
    }

    @Test
    void testLookup_whenNonExistentEntries_thenReturnsEmpty() {
        assertTrue(this.templateSource.lookup("nonExistingKey", Locale.ENGLISH).isBlank());
    }
}
