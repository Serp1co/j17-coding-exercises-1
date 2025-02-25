package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

// main class
public class StreamProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(StreamProcessRunner.class);

    public static void main(String[] args) {
        logger.info("Streams exercises start");
        processNumbers(); // methods
        processCarBrands();
        processEmployeeStatistics();
        processHeroes();
        processBooks();
    }

    private static void processNumbers() {
        // es.1
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(10);
        logger.info("numbers: {}", numbers);

        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        logger.info("Even numbers: {}", evenNumbers);

        List<Integer> oddNumbers = numbers.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * 3)
                .collect(Collectors.toList());
        logger.info("Odd numbers * 3: {}", oddNumbers);
        logger.info("\n=====================\n");

        // es.2
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers2 = numbers2.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        Integer sum = evenNumbers2.stream()
                .reduce(0, (a, b) -> a + b);
        logger.info("sum even numbers: {}", sum);

        List<Integer> oddNumbers2 = numbers2.stream()
                .filter(n -> n % 2 != 0)
                .collect(Collectors.toList());
        Integer product = oddNumbers2.stream()
                .reduce(1, (a, b) -> a * b);
        logger.info("product odd numbers: {}", product);


    }

    private static void processCarBrands() {

        // es.3
        List<String> carBrands = Arrays.asList(
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
        logger.info("car brands: {}", carBrands);

        // filtered by length
        List<String> filteredBrandsByLength = carBrands.stream()
                .filter(brand -> brand.length() <= 6)
                .collect(Collectors.toList());
        logger.info("brands filtered by length: {}", filteredBrandsByLength);

        // filtered by char
        List<String> filteredBrandsByCharA = carBrands.stream()
                .filter(brand -> brand.startsWith("A"))
                .collect(Collectors.toList());
        logger.info("brands starts with A: {}", filteredBrandsByCharA);


        // set filtered by char
        Set<String> filteredBrandsSet = carBrands.stream()
                .filter(brand -> brand.startsWith("Aud") || brand.startsWith("F"))
                .collect(Collectors.toSet());
        System.out.println("Brands starting with 'Aud' and 'F': " + filteredBrandsSet);

        // set filtered by brands name
        Set<String> filteredBrandsAudi = carBrands.stream()
                .filter(brand -> brand.equals("Audi"))
                .collect(Collectors.toSet());
        System.out.println("Audi: " + filteredBrandsAudi);


        // map filtered by prefix
        List<String> prefixes = Arrays.asList("A", "F", "B");

        Map<String, List<String>> groupedBrands = carBrands.stream()
                .filter(brand -> prefixes.stream().anyMatch(prefix -> brand.startsWith(prefix)))
                .collect(Collectors.groupingBy(brand -> prefixes.stream().filter(brand::startsWith).findFirst().get()));
        groupedBrands.forEach((prefix, brands) ->
                System.out.println("Brands starting with '" + prefix + "': " + brands));

    }

    private static void processEmployeeStatistics() {
        // Es.4
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 5000),
                new Employee("Roberto", "HR", 4000),
                new Employee("Carlo", "IT", 6000),
                new Employee("Davide", "Finance", 7000),
                new Employee("Enrico", "Finance", 8000),
                new Employee("Luisa", "IT", 5500),
                new Employee("Gino", "Finance", 5200),
                new Employee("Sandro", "IT", 6500),
                new Employee("Leila", "IT", 5600),
                new Employee("Giorgia", "HR", 5800)
        );

        // grouped by department
        Map<String, Long> departmentCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        logger.info("Counts of departments: {}", departmentCounts);

        // grouped by avarage salary
        Map<String, Double> averageSalaries = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        logger.info("Avarage salary: {}", averageSalaries);

        // partition based on seniority
        Map<Boolean, List<Employee>> partitionBySeniority = employees.stream()
                .collect(Collectors.partitioningBy(Employee::isJunior));
        logger.info("Junior Employees: {}", partitionBySeniority.get(true));
        logger.info("Senior Employees: {}", partitionBySeniority.get(false));

    }

    private static void processHeroes() {
        //es .5
        List<String> Marvel = Arrays.asList("Hulk", "Spiderman", "Thor", "Ironman");
        List<String> DC = Arrays.asList("Wwoman", "Flash", "Superman", "Batman");
        List<String> WSJ = Arrays.asList("Naruto", "Luffy", "Saitama", "Goku");

        // combining the hero's lists
        List<List<String>> listOfLists = Arrays.asList(Marvel, DC, WSJ);
        logger.info("before flatMap: {}", listOfLists);

        // flat map
        List<String> listofHeroes = listOfLists.stream()
                .flatMap(list -> list.stream())
                .sorted() // order
                .collect(Collectors.toList());
        logger.info("after flatMap: {}", listofHeroes);

    }

    private static void processBooks() {
        //es.6
        // counts lines starting with 'I'
        try (Stream<String> lines = Files.lines(Path.of("books.txt"))) {

            long i = lines.filter(line -> line.startsWith("I"))
                    .count();
            logger.info("The count of lines starting with 'I' is: " + i);
        } catch (IOException e) {

            logger.error("Error reading file: ", e);
        }


        // Eliminate duplicates and filter by word length
        try (Stream<String> lines = Files.lines(Path.of("books.txt"))) {
            // create wordList
            List<String> wordsList = lines
                    .flatMap(line -> Stream.of(line.split("\\W+")))
                    .collect(Collectors.toList());
            logger.info("word list: {} ", wordsList);

            // eliminate duplicates with Set
            Set<String> wordSet = wordsList.stream()
                    .collect(Collectors.toSet());
            logger.info("eliminate duplicates: {}", wordSet);

            //filtered by length
            List<String> filteredByLength = wordsList.stream()
                    .filter(word -> word.length() >= 6)
                    .collect(Collectors.toList());
            logger.info("words with length >= 6: {}", filteredByLength);

            List<String> filteredBrandsByChar = wordsList.stream()
                    .filter(word -> word.startsWith("S"))
                    .collect(Collectors.toList());
            logger.info("brands starts with S: {}", filteredBrandsByChar);

        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }


    }

}