package ir.digiexpress.translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranslatableTest {
    static class MockTranslatableImpl implements Translatable {
    }

    @Test
    void testGetKey_whenCalledDefaultImplementation_GeneratesKeyFromClassName() {
        var translatable = new MockTranslatableImpl();
        var expectedKey = "mockTranslatableImpl";
        assertEquals(expectedKey, translatable.getKey());
    }

    @Test
    void testGetParameters_whenCalledDefaultImplementation_ReturnsEmptyMap() {
        var translatable = new MockTranslatableImpl();
        assertTrue(translatable.getParameters().isEmpty());
    }
}