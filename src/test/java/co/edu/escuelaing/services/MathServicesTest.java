package co.edu.escuelaing.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MathServicesTest {

    @Test
    void squaresPositiveNumbers() {
        assertEquals(25, MathServices.square(5));
    }

    @Test
    void squaresZero() {
        assertEquals(0, MathServices.square(0));
    }

    @Test
    void squaresNegativeNumbers() {
        assertEquals(49, MathServices.square(-7));
    }
}
