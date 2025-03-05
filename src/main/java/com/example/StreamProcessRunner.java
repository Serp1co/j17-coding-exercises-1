package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.concurrent.ForkJoinPool;

// main
public class StreamProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(StreamProcessRunner.class);

    public static void main(String[] args) {
        logger.info("Streams exercises start");
        processNumbers(); // methods
        processCarBrands();
        processEmployeeStatistics();
        processHeroes();
        processBooks();
        // Es.7
        int[] minMax = IntStream.of(3, 7, 1, 9, 4, 2)
                .boxed()
                .collect(minMaxCollector()); // method

        logger.info("Min: {}, Max: {}", minMax[0], minMax[1]);
        // Es.8
        processParallelStream();
        processParallelFile();
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
                .toList();
        logger.info("Even numbers: {}", evenNumbers);

        List<Integer> oddNumbers = numbers.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * 3)
                .toList();
        logger.info("Odd numbers * 3: {}", oddNumbers);

        // es.2
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers2 = numbers2.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        Integer sum = evenNumbers2.stream()
                .reduce(0, Integer::sum);
        logger.info("sum even numbers: {}", sum);

        List<Integer> oddNumbers2 = numbers2.stream()
                .filter(n -> n % 2 != 0)
                .toList();
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
                .toList();
        logger.info("brands filtered by length: {}", filteredBrandsByLength);

        // filtered by char
        List<String> filteredBrandsByCharA = carBrands.stream()
                .filter(brand -> brand.startsWith("A"))
                .toList();
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
                .filter(brand -> prefixes.stream().anyMatch(brand::startsWith))
                .collect(Collectors.groupingBy(brand -> prefixes.stream()
                        .filter(brand::startsWith)
                        .findFirst()
                        .orElse("")
                ));
        groupedBrands.remove("");
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
                new Employee("Giorgio", "HR", 5800)
        );

        // grouped by department
        Map<String, Long> departmentCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        logger.info("Counts of departments: {}", departmentCounts);

        // grouped by average salary
        Map<String, Double> averageSalaries = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        logger.info("Average salary: {}", averageSalaries);

        // partition based on seniority
        Map<Boolean, List<Employee>> partitionBySeniority = employees.stream()
                .collect(Collectors.partitioningBy(Employee::isJunior));
        logger.info("Junior Employees: {}", partitionBySeniority.get(true));
        logger.info("Senior Employees: {}", partitionBySeniority.get(false));

    }

    private static void processHeroes() {
        //es .5
        List<String> Marvel = Arrays.asList("Hulk", "Spider man", "Thor", "Ironman");
        List<String> DC = Arrays.asList("Wonder Woman", "Flash", "Superman", "Batman");
        List<String> WSJ = Arrays.asList("Naruto", "Luffy", "Saitama", "Goku");

        // combining the hero's lists
        List<List<String>> listOfLists = Arrays.asList(Marvel, DC, WSJ);
        logger.info("Before flatMap: {}", listOfLists);

        // flat map
        List<String> listHeroes = listOfLists.stream()
                .flatMap(Collection::stream)
                .sorted() // order
                .toList();
        logger.info("After flatMap: {}", listHeroes);

    }

    private static void processBooks() {
        // count lines starting with 'I'
        try (InputStream inputStream = StreamProcessRunner.class.getClassLoader().getResourceAsStream("books.txt")) {
            if (inputStream == null) {
                logger.error("The file book.txt not found in resource folder.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                long count = reader.lines()
                        .filter(line -> line.startsWith("I"))
                        .count();
                logger.info("The count of lines starting with 'I' is: {}", count);
            }
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }

        // duplicates and filter by length
        try (InputStream inputStream = StreamProcessRunner.class.getClassLoader().getResourceAsStream("books.txt")) {
            if (inputStream == null) {
                logger.error("The file book.txt not found in resource folder.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                // Create wordList
                List<String> wordsList = reader.lines()
                        .flatMap(line -> Stream.of(line.split("\\W+")))
                        .toList();
                logger.info("Books Word list: {}", wordsList);

                // Eliminate duplicates with Set
                Set<String> wordSet = new HashSet<>(wordsList);
                logger.info("Books Words without duplicates: {}", wordSet);

                // filter by length
                List<String> filteredByLength = wordsList.stream()
                        .filter(word -> word.length() >= 6)
                        .toList();
                logger.info("Books Words with length >= 6: {}", filteredByLength);

                // words with 'S'
                List<String> filteredBrandsByChar = wordsList.stream()
                        .filter(word -> word.startsWith("S"))
                        .toList();
                logger.info("Books Words starting with S: {}", filteredBrandsByChar);
            }
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }
    }


    // Supplier, accumulator, combiner, finisher
    // Es .7
    private static Collector<Integer, ?, int[]> minMaxCollector() {
        return Collector.of(
                () -> new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}, // container for min max
                (acc, val) -> { // update min max
                    acc[0] = Math.min(acc[0], val);
                    acc[1] = Math.max(acc[1], val);
                },
                (acc1, acc2) -> { // combine accumulators
                    acc1[0] = Math.min(acc1[0], acc2[0]);
                    acc1[1] = Math.max(acc1[1], acc2[1]);
                    return acc1;
                },
                acc -> acc // result
        );
    }

    //  Sequential Streams: Process elements in a sequential manner, one element at a time
    //  Parallel Streams: Process elements in parallel, utilizing multiple CPU cores.
    //  possible zombie thread
    private static void processParallelStream() {
        List<Integer> binaries = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        ForkJoinPool customPool = new ForkJoinPool(4); // numbers of threads

        List<Integer> parallelB = customPool.submit(() ->
                binaries.parallelStream()
                        .filter(n -> n % 2 == 0)
                        .sorted()
                        .collect(Collectors.toList())
        ).join();
        logger.info("Parallel stream: {}", parallelB);

        customPool.shutdown(); // close threads
    }

    private static void processParallelFile() {
        // ClassLoader
        InputStream inputStream = StreamProcessRunner.class.getClassLoader().getResourceAsStream("books.txt");

        if (inputStream == null) {
            logger.error("The file books.txt not found in resource folder.");
            return;
        }

        // try-with-resources
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines()
                    .parallel()
                    .forEach(System.out::println);
        } catch (IOException e) {
            logger.error("Error file read: ", e);
        }
    }

}