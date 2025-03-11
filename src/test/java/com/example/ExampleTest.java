package com.example;

import com.example.models.Employee;
import com.example.runner.TestProcessRunner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    @BeforeAll
    static void setupAll() {
        System.out.println("Performed once before all tests, creating DB connection");
    }

    @BeforeEach
    void setup() {
        System.out.println("Performed before each test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Performed after each test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Performed once after all test, closing DB connection");
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
    @DisplayName("test with asserThrows NumberFormatException")
    void testIsNumberEven_ThrowsException() {

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> Integer.parseInt("1a"));

        assertTrue(exception.getMessage().contains("For input string"),
                "Expected message to contain 'For input string'");
    }

    @Test
    @DisplayName("test with assertThrows ArithmeticException")
    void testDivisionByZero_ThrowsException() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            int division = 10 / 0;
        });

        String expectedMessage = "/ by zero";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected message to contain: " + expectedMessage);
    }


    @Test
    void testIsNumberOdd() {
        TestProcessRunner runnerT = new TestProcessRunner();

        assertTrue(runnerT.isNumberOdd((1)), "number must be even");

        assertFalse(runnerT.isNumberOdd(2), "number must be odd");

    }

    @Test
    void testAdditionEquals() {
        int result = 2 + 3;
        assertEquals(5, result, "sum must be 5");
    }

    @Test
    void testAdditionNotEquals() {
        int result = 2 + 3;
        assertNotEquals(6, result, "sum must be 5");
    }

    @Test
    void testAdditionTrue() {
        int result = 2 + 3;
        assertTrue(result == 5, "sum must be 5");
    }

    @Test
    void testAdditionFalse() {
        int result = 2 + 3;
        assertFalse(result == 8, "sum must be 5");
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
    @DisplayName("TempDir test")
        // create temporary file/dir -> access to file/dir -> remove file/dir
    void createFileInTempDirTest(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("testFile.txt");
        Files.write(tempFile, "New file write.".getBytes());

        assertTrue(Files.exists(tempFile));
        assertEquals("New file write.", new String(Files.readAllBytes(tempFile)));
    }

    @Test
    @DisplayName("test with assertAll with class Employee")
    void AssertAllTest() {
        Employee employee = new Employee("Graziano", "Cybersecurity", 4000);
        assertAll(
                "Grouped Assertion of Employee",
                () -> assertEquals("Leonardo", employee.getName(), "Name should be Leonardo"),
                () -> assertEquals("Consulting", employee.getDepartment(), "Department should be Cybersecurity"),
                () -> assertEquals(4500, employee.getSalary(), "Salary should be 4000")
        );
    }

    @Test
    @DisplayName("test with boundary value")
    void BoundaryTest() {

    }
}

