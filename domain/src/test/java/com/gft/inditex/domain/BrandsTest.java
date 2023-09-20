package com.gft.inditex.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrandsTest {
    @Test
    void should_return_correct_enum_value() {
        assertEquals(1, Brands.ZARA.getValue());
    }

    @Test
    void should_throw_exception_for_invalid_value() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Brands.getByValue(999);  // Use a value that's not associated with any Brands enum
        });
        assertEquals("No enum constant for value: 999", exception.getMessage());
    }
}