package ir.digiexpress.translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranslationTest {
    @Test
    void testMessage_whenCalled_thenReturnsTranslatableMessageWithNoParams() {
        var expectedKey = "aKey";
        var result = Translation.message(expectedKey);
        assertEquals(expectedKey, result.getKey());
        assertTrue(result.getParameters().isEmpty());
    }
}