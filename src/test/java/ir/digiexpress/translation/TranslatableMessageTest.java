package ir.digiexpress.translation;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;


class TranslatableMessageTest {
    @Test
    void testConstruction_whenKeyIsPassedNullOrEmpty_throwsError() {
        var parameters = new HashMap<String, Object>();
        assertThrows(IllegalArgumentException.class, () -> new TranslatableMessage(null, parameters));
        assertThrows(IllegalArgumentException.class, () -> new TranslatableMessage("", parameters));
    }

    @Test
    void testConstruction_whenParametersArePassedNull_thenNoErrorsAndCreatesEmptyParameterMapping() {
        Map<String, Object> expectedParameters = Collections.emptyMap();
        var translatable = new TranslatableMessage("test", null);
        assertEquals(expectedParameters, translatable.getParameters());
    }

    @Test
    void testGetters_whenConstructed_thenMatchesInfo() {
        var expectedKey = "testKey";
        var expectedParams = Map.of("param1", (Object) "val1", "param2", "val2");

        var translatable = new TranslatableMessage(expectedKey, expectedParams);

        assertEquals(expectedKey, translatable.getKey());
        assertEquals(expectedParams, translatable.getParameters());
    }

    @Test
    void testPut_whenPassedNullOrEmptyParamKey_thenThrowsError() {
        var translatable = new TranslatableMessage("testKey");
        assertThrows(IllegalArgumentException.class, () -> translatable.put(null, "testParam"));
        assertThrows(IllegalArgumentException.class, () -> translatable.put("", "testParam"));
    }

    @Test
    void testPut_whenCalled_thenAddsToParams() {
        var expectedKey = "testKey";
        var expectedParams = Collections.emptyMap();

        var translatable = new TranslatableMessage(expectedKey);
        assertEquals(expectedKey, translatable.getKey());
        assertEquals(expectedParams, translatable.getParameters());

        expectedParams = Map.of("param1", "val1", "param2", "val2");
        translatable = translatable
                .put("param1", "val1")
                .put("param2", "val2");
        assertEquals(expectedKey, translatable.getKey());
        assertEquals(expectedParams, translatable.getParameters());
    }

    @Test
    void testPutIfNotExists_whenPassedNullOrEmptyParamKey_thenThrowsError() {
        var translatable = new TranslatableMessage("testKey");
        assertThrows(IllegalArgumentException.class, () -> translatable.putIfAbsent(null, (s) -> "val"));
        assertThrows(IllegalArgumentException.class, () -> translatable.putIfAbsent(null, () -> "val"));
        assertThrows(IllegalArgumentException.class, () -> translatable.putIfAbsent("", (s) -> "val"));
        assertThrows(IllegalArgumentException.class, () -> translatable.putIfAbsent("", () -> "val"));
    }

    @Test
    void testPutIfNotExists_whenPassedNullMappingFunctionOrSupplier_thenThrowsError() {
        var translatable = new TranslatableMessage("testKey");
        assertThrows(IllegalArgumentException.class,
                () -> translatable.putIfAbsent("testKey", (Function<String, Object>) null));
        assertThrows(IllegalArgumentException.class,
                () -> translatable.putIfAbsent("testKey", (Supplier<Object>) null));
    }

    @Test
    void testPutIfNotExists_whenPassedAlreadyExistingParamKey_thenDoesNotGetCalled() {
        var expectedParamKey = "paramKey";
        var expectedParamValue = 0;
        var translatable = new TranslatableMessage("testKey").put(expectedParamKey, expectedParamValue);
        var called = new AtomicBoolean(false);

        translatable = translatable.putIfAbsent("paramKey", (s) -> {
            called.set(true);
            return expectedParamValue;
        });
        translatable = translatable.putIfAbsent("paramKey", () -> {
            called.set(true);
            return expectedParamValue;
        });
        assertFalse(called.get());
        assertEquals(Map.of(expectedParamKey, expectedParamValue), translatable.getParameters());
    }

    @Test
    void testPutIfNotExistsWithFunction_whenPassedNewParamKey_thenCalculatesAndPuts() {
        var expectedParamKey = "paramKey";
        var expectedParamValue = 0;
        var translatable = new TranslatableMessage("testKey");
        var called = new AtomicBoolean(false);

        translatable = translatable.putIfAbsent("paramKey", (s) -> {
            called.set(true);
            return expectedParamValue;
        });
        assertTrue(called.get());
        assertEquals(Map.of(expectedParamKey, expectedParamValue), translatable.getParameters());
    }

    @Test
    void testPutIfNotExistsWithSupplier_whenPassedNewParamKey_thenCalculatesAndPuts() {
        var expectedParamKey = "paramKey";
        var expectedParamValue = 0;
        var translatable = new TranslatableMessage("testKey");
        var called = new AtomicBoolean(false);

        translatable = translatable.putIfAbsent("paramKey", () -> {
            called.set(true);
            return expectedParamValue;
        });
        assertTrue(called.get());
        assertEquals(Map.of(expectedParamKey, expectedParamValue), translatable.getParameters());
    }

    @Test
    void testToString_whenCalled_thenOk() {
        var expectedResult = "Translatable(key=test, parameters={p1=v1, p2=v2})";
        var translatable = new TranslatableMessage("test")
                .put("p1", "v1")
                .put("p2", "v2");

        assertEquals(expectedResult, translatable.toString());
    }
}