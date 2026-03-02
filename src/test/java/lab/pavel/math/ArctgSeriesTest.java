package lab.pavel.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArctgSeriesTest {

    @ParameterizedTest
    @CsvSource({
            "0.0, 0.0",
            "0.5, 0.4636476090",
            "-0.5, -0.4636476090",
            "1.0, 0.7853981634",
            "-1.0, -0.7853981634",
            "0.25, 0.2449786631",
            "-0.25, -0.2449786631"
    })
    void arctg_shouldMatchTableValues(double x, double expected) {
        double actual = ArctgSeries.arctg(x, 5_000);
        assertEquals(expected, actual, 1e-6);
    }

    @Test
    void arctg_shouldThrowForNonPositiveTerms() {
        assertThrows(IllegalArgumentException.class, () -> ArctgSeries.arctg(0.2, 0));
    }

    @Test
    void arctg_shouldThrowForOutOfRangeX() {
        assertThrows(IllegalArgumentException.class, () -> ArctgSeries.arctg(1.1, 10));
        assertThrows(IllegalArgumentException.class, () -> ArctgSeries.arctg(-1.1, 10));
    }
}

