package ir.digiexpress.translation.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpringTemplateSourceTest {
    private final String defaultMessageTemplate = "defaultMessageTemplate";
    private final String defaultMessageKey = "defaultKey";
    private final String nonExistentKey = "nonExistentKey";
    private final Locale defaultLocale = Locale.ENGLISH;
    private SpringTemplateSource springTemplateSource;

    @BeforeEach
    void setUp() {
        var springMessageSource = mock(MessageSource.class);
        when(springMessageSource.getMessage(this.defaultMessageKey, null, this.defaultLocale)).thenReturn(this.defaultMessageTemplate);
        when(springMessageSource.getMessage(this.nonExistentKey, null, this.defaultLocale)).thenReturn(null);
        when(springMessageSource.getMessage(this.nonExistentKey, null, Locale.FRENCH)).thenReturn(null);
        this.springTemplateSource = new SpringTemplateSource(springMessageSource);
    }

    @Test
    void testLookup_whenPassedExistingEntries_thenReturnsOk() {
        assertEquals(defaultMessageTemplate, this.springTemplateSource.lookup(this.defaultMessageKey, this.defaultLocale));
    }

    @Test
    void testLookup_whenPassedNonExistentEntries_thenReturnsEmpty() {
        assertTrue(this.springTemplateSource.lookup(this.nonExistentKey, this.defaultLocale).isBlank());
        assertTrue(this.springTemplateSource.lookup(this.defaultMessageKey, Locale.FRENCH).isBlank());
    }
}
