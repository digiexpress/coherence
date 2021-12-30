package ir.digiexpress.translation.injector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecursiveParameterInjectorTest {
    private RecursiveParameterInjector injector;

    @BeforeEach
    void setUp() {
        this.injector = new RecursiveParameterInjector("{", "}", ":-", '\\');
    }

    @Test
    void testDeclareParameter_whenHasDefaultValue_thenInjectsIt() {
        var expected = "{hello:-defaultValue}";
        assertEquals(expected, this.injector.declareParameter("hello", "defaultValue"));
    }

    @Test
    void testDeclareParameter_whenDoesntHaveDefaultValue_thenDoesntInjectIt() {
        var expected = "{hello}";
        assertEquals(expected, this.injector.declareParameter("hello"));
    }

    @Test
    void testDeclareParameter_whenParamKeyIsNullOrEmpty_thenThrowsError() {
        assertThrows(
                IllegalArgumentException.class,
                () -> this.injector.declareParameter(null, "defaultValue")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> this.injector.declareParameter("", "defaultValue")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> this.injector.declareParameter(null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> this.injector.declareParameter("")
        );
    }

    @Test
    void testDeclareParameter_whenDefaultValueIsNullOrEmpty_thenInjectsIt() {
        var expected = "{hello:-}";
        assertEquals(expected, this.injector.declareParameter("hello", null));
        assertEquals(expected, this.injector.declareParameter("hello", ""));
    }

    @Test
    void testInject_whenSimpleCase_thenOk() {
        var inputTemplate = "the key {k1} is ok";
        var inputParameterMapping = Map.of("k1", (Object) "v1");
        var expected = "the key v1 is ok";
        assertEquals(expected, this.injector.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testInject_whenMultipleInjectionsAndDefaultValues_thenOk() {
        var inputTemplate = "a {k1} b {k2} c {k3:-v3} d {k4} e {k5:-v5} f {k6:-v7}";
        var inputParameterMapping = Map.of(
                "k1", (Object) "v1",
                "k2", "v2",
                "k4", "v4",
                "k6", "v6"
        );
        var expected = "a v1 b v2 c v3 d v4 e v5 f v6";
        assertEquals(expected, this.injector.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testInject_whenSimpleRecursiveCase_thenOk() {
        var inputTemplate = "a {k1} b";
        var inputParameterMapping = Map.of(
                "k1", (Object) "{k2}",
                "k2", "{k3}",
                "k3", "k4"
        );
        var expected = "a k4 b";
        assertEquals(expected, this.injector.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testInject_whenComplexRecursiveCase_thenOk() {
        var inputTemplate = "a k{k{k{v}}} b";
        var inputParameterMapping = Map.of(
                "v", (Object) "1",
                "k1", "2",
                "k2", "3"
        );
        var expected = "a k3 b";
        assertEquals(expected, this.injector.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testInject_whenAllCases_thenOk() {
        var inputTemplate = "a k{k{k{v}}} b {k2} c {k3:-default}";
        var inputParameterMapping = Map.<String, Object>of(
                "v", "1",
                "k1", "2",
                "k2", "3"
        );
        var expected = "a k3 b 3 c default";
        assertEquals(expected, this.injector.inject(inputTemplate, inputParameterMapping));
    }

    @Test
    void testInject_whenPassedOpenTemplateMessage_thenActAsExpected() {
        var inputs = List.of(
                "{open",
                "{hey:-oops} {open:-secondOops",
                "{hey:-oops} {open:-secondOops {open}"
        );
        var inputMappings = List.of(
                Map.<String, Object>of("open", "another"),
                Map.<String, Object>of("open", "closed"),
                Map.<String, Object>of("open", "closed")
        );
        var expectedTranslations = List.of(
                "{open",
                "oops {open:-secondOops",
                "oops {open:-secondOops {open}"
        );
        for (int testIdx = 0; testIdx < inputs.size(); testIdx++)
            assertEquals(
                    expectedTranslations.get(testIdx),
                    this.injector.inject(inputs.get(testIdx), inputMappings.get(testIdx))
            );
    }
}
