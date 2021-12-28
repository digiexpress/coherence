package ir.digiexpress.translation.translatable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranslatableDomainTest {
    static class MockTranslatableDomain extends TranslatableDomain {
    }

    @Test
    void testGetKey_whenCalledDefaultImplementation_GeneratesKeyFromClassName() {
        var translatable = new MockTranslatableDomain();
        var expectedKey = "mockTranslatableDomain";
        assertEquals(expectedKey, translatable.getKey());
    }

    @Test
    void testGetParameters_whenCalledDefaultImplementation_ReturnsEmptyMap() {
        var translatable = new MockTranslatableDomain();
        assertTrue(translatable.getParameters().isEmpty());
    }
}