package ir.digiexpress.translation;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    @Test
    void testGet_whenCalledOnNonexistentKey_thenReturnsNull() {
        var translatable = new TranslatableMessage("testKey");
        assertNull(translatable.get("paramKey"));
    }

    @Test
    void testGet_whenCalledOnExistentKey_thenOk() {
        var expectedValue = "aValue";
        var expectedKey = "aKey";
        var translatable = new TranslatableMessage("testKey").put(expectedKey, expectedValue);
        assertEquals(expectedValue, translatable.get(expectedKey));
    }

    @Test
    void testGetOrElse_whenCalledOnNonexistentKey_thenReturnsAlternative() {
        var expectedValue = "aValue";
        var translatable = new TranslatableMessage("testKey");
        assertEquals(expectedValue, translatable.getOrElse("paramKey", expectedValue));
    }

    @Test
    void testGetOrElse_whenCalledOnExistentKey_thenReturnsTheOriginalValue() {
        var expectedValue = "aValue";
        var expectedKey = "aKey";
        var translatable = new TranslatableMessage("testKey").put(expectedKey, expectedValue);
        assertEquals(expectedValue, translatable.getOrElse(expectedKey, "anotherKey"));
    }

    @Test
    void testEquals_whenCalledOnDissimilarReferences_returnsTrue() {
        var t1 = new TranslatableMessage("testKey").put("aKey", "aValue");
        var t2 = new TranslatableMessage("testKey").put("aKey", "aValue");
        assertEquals(t1, t2);
    }

    @Test
    void testHashCode_whenCalledOnDissimilarReferences_returnsTrue() {
        var t1 = new TranslatableMessage("testKey").put("aKey", "aValue");
        var t2 = new TranslatableMessage("testKey").put("aKey", "aValue");
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testEquals_whenCalledOnDissimilarParametersOrKey_returnsFalse() {
        var objectList = List.of(
                new TranslatableMessage("testKey"),
                new TranslatableMessage("testKey").put("aKey", "aValue"),
                new TranslatableMessage("anotherKey").put("aKey", "aValue"),
                new TranslatableMessage("anotherKey").put("otherKey", "otherValue"),
                new Object());

        for (int i = 0; i < objectList.size(); i++)
            for (int j = 0; j < objectList.size(); j++)
                if (i != j)
                    assertNotEquals(objectList.get(i).hashCode(), objectList.get(j).hashCode());
    }
}