package pe.edu.vallegrande;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Debe sumar dos números correctamente")
    void testAdd() {
        assertEquals(8.0, calculator.add(5.0, 3.0), 0.0001);
        assertEquals(-2.0, calculator.add(-5.0, 3.0), 0.0001);
    }

    @Test
    @DisplayName("Debe restar dos números correctamente")
    void testSubtract() {
        assertEquals(2.0, calculator.subtract(5.0, 3.0), 0.0001);
        assertEquals(-8.0, calculator.subtract(-5.0, 3.0), 0.0001);
    }

    @Test
    @DisplayName("Debe multiplicar dos números correctamente")
    void testMultiply() {
        assertEquals(15.0, calculator.multiply(5.0, 3.0), 0.0001);
        assertEquals(0.0, calculator.multiply(5.0, 0.0), 0.0001);
        assertEquals(-15.0, calculator.multiply(-5.0, 3.0), 0.0001);
    }

    @Test
    @DisplayName("Debe dividir dos números correctamente")
    void testDivide() {
        assertEquals(2.5, calculator.divide(5.0, 2.0), 0.0001);
        assertEquals(-2.0, calculator.divide(6.0, -3.0), 0.0001);
    }

    @Test
    @DisplayName("Debe lanzar excepción al dividir por cero")
    void testDivideByZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10.0, 0.0)
        );
        assertEquals("No se puede dividir entre cero", exception.getMessage());
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "1.0, 1.0, 2.0",
            "10.5, 4.5, 15.0",
            "-2.0, -3.0, -5.0"
    })
    @DisplayName("Pruebas parametrizadas para la suma")
    void testParameterizedAdd(double a, double b, double expected) {
        assertEquals(expected, calculator.add(a, b), 0.0001);
    }

    @Test
    @DisplayName("Fallo intencional para probar el post failure de Jenkins")
    void testIntentionalFailureForJenkins() {
        assertEquals(
                5.0,
                calculator.add(2.0, 2.0),
                0.0001,
                "Fallo intencional para verificar la notificacion de Jenkins"
        );
    }
}