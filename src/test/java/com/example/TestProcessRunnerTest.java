package com.example;

import com.example.runner.TestProcessRunner;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestProcessRunnerTest {

    @BeforeAll
    static void setupAll() {
        System.out.println("Performed once before all tests, creating DB connection");
    }

    @BeforeEach
    void setup() {
        System.out.println("Performed before each test");
    }

    @Test
    void testIsNumberEven() {
        TestProcessRunner runnerT = new TestProcessRunner();

        assertTrue(runnerT.isNumberEven((2)), "number must be even");
        assertTrue(runnerT.isNumberEven((0)));
        assertTrue(runnerT.isNumberEven((-2)));

        assertFalse(runnerT.isNumberEven(3), "number must be odd");
        assertFalse(runnerT.isNumberEven(7));
        assertFalse(runnerT.isNumberEven(-3));
    }

    @Test
    void testIsNumberEven_ThrowsException() {

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> Integer.parseInt("1a"));

        assertTrue(exception.getMessage().contains("For input string"),
                "Expected message to contain 'For input string'");
    }

    @Test
    void testIsNumberOdd() {
        TestProcessRunner runnerT = new TestProcessRunner();

        assertTrue(runnerT.isNumberOdd((1)), "number must be even");

        assertFalse(runnerT.isNumberOdd(2), "number must be odd");

    }

    @Test
    void testAddition() {
        int result = 2 + 3;
        assertEquals(5, result, "sum must be 5");
    }


    @Test
    void testMultiplication() {
        int result = 2 * 3;
        assertEquals(6, result, "product must be 6");
    }

    @Test
    void testDivision() {
        int result = 6 / 2;
        assertEquals(3, result, " division must be 3");
    }

    @Test
    void testDivisionByZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            int division = 10 / 0;
        });

        String expectedMessage = "/ by zero";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected message to contain: " + expectedMessage);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Performed after each test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Performed once after all test, closing DB connection");
    }
}

