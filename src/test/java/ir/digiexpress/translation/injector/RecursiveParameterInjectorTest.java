package ir.digiexpress.translation.injector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecursiveParameterInjectorTest {
    private RecursiveParameterInjector resolver;

    @BeforeEach
    void setUp() {
        this.resolver = new RecursiveParameterInjector("{", "}", ":-");
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
        assertEquals(expected, this.resolver.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testResolve_whenMultipleInjectionsAndDefaultValues_thenOk() {
        var inputTemplate = "a {k1} b {k2} c {k3:-v3} d {k4} e {k5:-v5} f {k6:-v7}";
        var inputParameterMapping = Map.of(
                "k1", (Object) "v1",
                "k2", "v2",
                "k4", "v4",
                "k6", "v6"
        );
        var expected = "a v1 b v2 c v3 d v4 e v5 f v6";
        assertEquals(expected, this.resolver.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testResolve_whenSimpleRecursiveCase_thenOk() {
        var inputTemplate = "a {k1} b";
        var inputParameterMapping = Map.of(
                "k1", (Object) "{k2}",
                "k2", "{k3}",
                "k3", "k4"
        );
        var expected = "a k4 b";
        assertEquals(expected, this.resolver.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testResolve_whenComplexRecursiveCase_thenOk() {
        var inputTemplate = "a k{k{k{v}}} b";
        var inputParameterMapping = Map.of(
                "v", (Object) "1",
                "k1", "2",
                "k2", "3"
        );
        var expected = "a k3 b";
        assertEquals(expected, this.resolver.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testResolve_whenAllCases_thenOk() {
        var inputTemplate = "a k{k{k{v}}} b {k2} c {k3:-default}";
        var inputParameterMapping = Map.of(
                "v", (Object) "1",
                "k1", "2",
                "k2", "3"
        );
        var expected = "a k3 b 3 c default";
        assertEquals(expected, this.resolver.inject(inputTemplate, inputParameterMapping));
    }
}