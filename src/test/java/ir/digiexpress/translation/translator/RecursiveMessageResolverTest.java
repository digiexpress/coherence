package ir.digiexpress.translation.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecursiveMessageResolverTest {
    private RecursiveMessageResolver resolver;

    @BeforeEach
    void setUp() {
        this.resolver = new RecursiveMessageResolver("{", "}", ":-");
    }

    @Test
    void testDeclareParameter_whenHasDefaultValue_thenInjectsIt() {
        var expected = "{hello:-defaultValue}";
        assertEquals(expected, this.resolver.declareParameter("hello", "defaultValue"));
    }

    @Test
    void testDeclareParameter_whenDoesntHaveDefaultValue_thenDoesntInjectIt() {
        var expected = "{hello}";
        assertEquals(expected, this.resolver.declareParameter("hello"));
    }

    @Test
    void testDeclareParameter_whenParamKeyIsNullOrEmpty_thenThrowsError() {
        assertThrows(IllegalArgumentException.class,
                () -> this.resolver.declareParameter(null, "defaultValue"));
        assertThrows(IllegalArgumentException.class,
                () -> this.resolver.declareParameter("", "defaultValue"));
        assertThrows(IllegalArgumentException.class,
                () -> this.resolver.declareParameter(null));
        assertThrows(IllegalArgumentException.class,
                () -> this.resolver.declareParameter(""));
    }

    @Test
    void testDeclareParameter_whenDefaultValueIsNullOrEmpty_thenInjectsIt() {
        var expected = "{hello:-}";
        assertEquals(expected, this.resolver.declareParameter("hello", null));
        assertEquals(expected, this.resolver.declareParameter("hello", ""));
    }

    @Test
    void testResolve_whenSimpleCase_thenOk() {
        var inputTemplate = "the key {k1} is ok";
        var inputParameterMapping = Map.of("k1", (Object) "v1");
        var expected = "the key v1 is ok";
        assertEquals(expected, this.resolver.resolve(inputTemplate, inputParameterMapping));
    }
}