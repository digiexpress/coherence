package ir.digiexpress.utils;

public class Assertions {
    private Assertions() {
    }

    /**
     * Utility method for the library that asserts a given String is not null or blank
     *
     * @param value      passed value to check
     * @param identifier identifier of the value
     * @throws IllegalArgumentException if the value is null or empty
     */
    public static void notEmpty(final String value, final String identifier) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException(String.format("%s cannot be null", identifier));
        if (value.isBlank()) throw new IllegalArgumentException(String.format("%s cannot be blank", identifier));
    }
}
