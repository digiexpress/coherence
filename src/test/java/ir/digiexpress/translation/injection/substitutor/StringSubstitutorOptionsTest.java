package ir.digiexpress.translation.injection.substitutor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StringSubstitutorOptionsTest {
    private StringSubstitutorOptions anOption;
    private StringSubstitutorOptions sameOption;
    private StringSubstitutorOptions anotherOption;

    @BeforeEach
    void setup() {
        this.anOption = new StringSubstitutorOptions("{", "}", ":", '\\', true);
        this.sameOption = new StringSubstitutorOptions("{", "}", ":", '\\', true);
        this.anotherOption = new StringSubstitutorOptions("{", "{", "!", '\\', false);
    }

    @Test
    void testEquals_whenPassedEqualObjects_thenReturnsTrue() {
        assertEquals(this.anOption, this.sameOption);
        assertEquals(this.anOption, this.anOption);
    }

    @Test
    void testEquals_whenPassedNull_thenReturnsFalse() {
        assertNotEquals(null, this.anOption);
    }

    @Test
    void testEquals_whenPassedUnequalObjects_thenReturnsFalse() {
        assertNotEquals(this.anOption, this.anotherOption);
    }

    @Test
    void testHashCode_whenCalledOnEqualObjects_thenReturnsTheSame() {
        assertEquals(this.anOption.hashCode(), this.sameOption.hashCode());
    }

    @Test
    void testHashCode_whenCalledOnUnequalObjects_thenReturnsDifferent() {
        assertNotEquals(this.anOption.hashCode(), this.anotherOption.hashCode());
    }
}
