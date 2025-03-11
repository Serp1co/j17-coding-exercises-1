package com.example;

import com.example.runner.CarBrandsResult;
import com.example.runner.StreamProcessRunner;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// launch mvn test -Dtest=StreamProcessRunnerTest#testName
@DisplayName("stream process test")
public class StreamProcessRunnerTest {
    private final List<Integer> numbersList = Arrays.asList(
            1, 2, 3, 4, 5
    );
    private static final List<String> carBrands = Arrays.asList(
            "Toyota",
            "Ford",
            "BMW",
            "Mercedes",
            "Audi",
            "Honda",
            "Volkswagen",
            "Chevrolet",
            "Nissan",
            "Porsche",
            "Fiat",
            "Smart",
            "Audi A1",
            "Audi A2",
            "Audi A3"
    );

    private StreamProcessRunner testS;
    // file for test processFile
    private static final String testFile = "books.txt";

    @BeforeEach
    void setUp() throws IOException {
        testS = new StreamProcessRunner();
        Path path = Paths.get(testFile);
        List<String> books = Arrays.asList(
                "Harry Potter 1",
                "Harry Potter 2",
                "Harry Potter 3",
                "Harry Potter 4",
                "Harry Potter 5"
        );
        Files.write(path, books);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFile));
    }

    @Test
    @DisplayName("should process file correctly with setup and teardown")
    public void testProcessFiles() {
        Path path = Paths.get(testFile);
        assertTrue(Files.exists(path), "Test file should exist before processing.");
        testS.processFiles(testFile);
    }


    @Test
    // title =input+action+output -> given+when+then
    public void processStreamNumberTest() {
        Map<String, Object> result = testS.processStreamNumbers(numbersList);

        // expected, actual
        assertEquals(Arrays.asList(2, 4), result.get("evenNumbers"));
        assertEquals(6, result.get("sum evenNumbers"));
        assertEquals(Arrays.asList(3, 9, 15), result.get("oddNumbers"));
        assertEquals(15, result.get("product EvenNumbers"));
    }

    @Test
    public void processStreamCarBrandsTest() {
        //if some elements are contained the test is valid
        CarBrandsResult result = testS.processCarBrands(carBrands);

        assertTrue(result.filteredByLength().containsAll(List.of("Toyota", "Ford", "BMW", "Audi", "Honda", "Nissan", "Fiat", "Smart")));

        assertTrue(result.filteredByA().contains("Audi"));
        assertTrue(result.filteredByA().contains("Audi A1"));

        assertTrue(result.filteredByAudF().contains("Audi"));
        assertTrue(result.filteredByAudF().contains("Ford"));

        assertEquals(Set.of("Audi"), result.filteredAudi());

        assertTrue(result.groupedByPrefix().containsKey("A"));
        assertTrue(result.groupedByPrefix().containsKey("F"));
        assertTrue(result.groupedByPrefix().containsKey("B"));
    }

    @Test
    public void dynamicCarBrandsTest() {
        CarBrandsResult result = testS.processCarBrands(carBrands);

        assertTrue(result.filteredByLength().stream().allMatch(brand -> brand.length() <= 6));
        assertTrue(result.filteredByA().stream().allMatch(brand -> brand.startsWith("A")));
        assertTrue(List.of("A", "F", "B").containsAll(result.groupedByPrefix().keySet()));
    }

}




