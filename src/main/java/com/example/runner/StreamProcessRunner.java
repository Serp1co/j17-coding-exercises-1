package com.example.runner;

import com.example.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.Collector;
import java.util.concurrent.ForkJoinPool;

public class StreamProcessRunner {
    private static final Logger LOG = LoggerFactory.getLogger(StreamProcessRunner.class);

    public static void main(String[] args) {
        // Es.7
        int[] minMax = IntStream.of(3, 7, 1, 9, 4, 2)
                .boxed()
                .collect(minMaxCollector()); // method

        LOG.info("Min: {}, Max: {}", minMax[0], minMax[1]);

    }

    public void processStreamNumbers(List<Integer> numbersList) {
        // es.1-2
        LOG.info("numbers list: {}", numbersList);
        List<Integer> evenNumbers = numbersList.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        LOG.info("Even numbers: {}", evenNumbers);
        Integer sum = evenNumbers.stream()
                .reduce(0, Integer::sum);
        LOG.info("sum even numbers: {}", sum);
        List<Integer> oddNumbers = numbersList.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * 3)
                .toList();
        LOG.info("Odd numbers * 3: {}", oddNumbers);
        List<Integer> oddNumbersProd = numbersList.stream()
                .filter(n -> n % 2 != 0)
                .toList();
        Integer product = oddNumbersProd.stream()
                .reduce(1, (a, b) -> a * b);
        LOG.info("product odd numbers: {}", product);

    }

    public void processCarBrands(List<String> carBrands) {
        // es.3
        LOG.info("car brands: {}", carBrands);
        // filtered by length
        List<String> filteredBrandsByLength = carBrands.stream()
                .filter(brand -> brand.length() <= 6)
                .toList();
        LOG.info("brands filtered by length: {}", filteredBrandsByLength);
        // filtered by char
        List<String> filteredBrandsByCharA = carBrands.stream()
                .filter(brand -> brand.startsWith("A"))
                .toList();
        LOG.info("brands starts with A: {}", filteredBrandsByCharA);
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

    public void processEmployeeStatistics(List<Employee> employees) {
        // Es.4
        // grouped by department
        Map<String, Long> departmentCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        LOG.info("Counts of departments: {}", departmentCounts);

        // grouped by average salary
        Map<String, Double> averageSalaries = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        LOG.info("Average salary: {}", averageSalaries);

        // partition based on seniority
        Map<Boolean, List<Employee>> partitionBySeniority = employees.stream()
                .collect(Collectors.partitioningBy(Employee::isJunior));
        LOG.info("Junior Employees: {}", partitionBySeniority.get(true));
        LOG.info("Senior Employees: {}", partitionBySeniority.get(false));

    }

    public void processHeroes(List<List<String>> listOfLists) {
        //es .5
        // combining the hero's lists
        LOG.info("Before flatMap: {}", listOfLists);
        // flat map
        List<String> listHeroes = listOfLists.stream()
                .flatMap(Collection::stream)
                .sorted() // order
                .toList();
        LOG.info("After flatMap: {}", listHeroes);

    }

    public void processFiles(String filePath) {
        try (InputStream inputStream = StreamProcessRunner.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                LOG.error("The file: {} not found in resource folder.", filePath);
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                // Create wordList
                List<String> wordsList = reader.lines()
                        .flatMap(line -> Stream.of(line.split("\\W+")))
                        .toList();
                LOG.info("Files Word list: {}", wordsList);
                // count lines starting with 'C'
                long wordsCount = wordsList.stream()
                        .filter(word -> word.startsWith("C"))
                        .count();
                LOG.info("The count of words starting with 'C' is: {}", wordsCount);
                // Eliminate duplicates with Set
                Set<String> wordSet = new HashSet<>(wordsList);
                LOG.info("Files Words without duplicates: {}", wordSet);
                // filter by length
                List<String> filteredByLength = wordsList.stream()
                        .filter(word -> word.length() >= 6)
                        .toList();
                LOG.info("Files Words with length >= 6: {}", filteredByLength);
                // words with 'S'
                List<String> filteredBrandsByChar = wordsList.stream()
                        .filter(word -> word.startsWith("S"))
                        .toList();
                LOG.info("Files Words starting with S: {}", filteredBrandsByChar);
            }
        } catch (IOException e) {
            LOG.error("Error reading file: ", e);
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
    public void processParallelStream(List<Integer> parallelList) {
        ForkJoinPool customPool = new ForkJoinPool(2); // numbers of threads

        List<Integer> parallel = customPool.submit(() ->
                parallelList.parallelStream()
                        .filter(n -> n % 2 == 0)
                        .sorted()
                        .toList()
        ).join();
        LOG.info("Parallel stream: {}", parallel);

        customPool.shutdown(); // close threads
    }

    public void processParallelFile(String filePath) {
        // ClassLoader
        InputStream inputStream = StreamProcessRunner.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            LOG.error("The file books.txt not found in resource folder.");
            return;
        }
        // try-with-resources
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines()
                    .parallel()
                    .forEach(System.out::println);
        } catch (IOException e) {
            LOG.error("Error file read: ", e);
        }
    }

}