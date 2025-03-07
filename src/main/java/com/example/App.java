package com.example;

import com.example.exceptions.InvalidAgeException;
import com.example.models.Employee;
import com.example.runner.ExceptionProcessRunner;
import com.example.runner.StreamProcessRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final List<Integer> numbersList = Arrays.asList(
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
    private static final List<Employee> employees = Arrays.asList(
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
    private static final int[] myList = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private static final List<String> Marvel = Arrays.asList("Hulk", "Spider man", "Thor", "Ironman");
    private static final List<String> DC = Arrays.asList("Wonder Woman", "Flash", "Superman", "Batman");
    private static final List<String> WSJ = Arrays.asList("Naruto", "Luffy", "Saitama", "Goku");
    private static final List<List<String>> Comics = Arrays.asList(Marvel, DC, WSJ);

    private static final List<Integer> parallelList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 1);

    private static final List<Integer> numbers = Arrays.asList(8, 4, 45, 65, 84, 1, 65, 123);

    private static final List<String> games = Arrays.asList("Crash", "Crash", "Crash 3", "Crash 2", "Crash 2", "Crash 3", "Crash 4", "Crash Bash", "CTR");


    public static void main(String[] args) throws IOException {
        // StreamProcess
        LOG.info("Streams exercises start");
        StreamProcessRunner runnerS = new StreamProcessRunner();
        // es.1
        runnerS.processStreamNumbers(numbersList);
        // es.3
        runnerS.processCarBrands(carBrands);
        // es.4
        runnerS.processEmployeeStatistics(employees);
        // es.5
        runnerS.processHeroes(Comics);
        // es.6
        runnerS.processFiles("books.txt");
        // es.7 Custom Collector
        int[] minMax = runnerS.calculateMinMax(numbers);
        LOG.info("Min: {}, Max: {}", minMax[0], minMax[1]);
        // es.8
        runnerS.processParallelStream(parallelList);
        runnerS.processParallelFile("books.txt");
        // extra
        runnerS.wordsFrequency(games);


        // ExceptionProcess
        LOG.info("Exceptions exercises start");
        ExceptionProcessRunner runnerE = new ExceptionProcessRunner();
        Scanner scanner = new Scanner(System.in);
        // es.1
        while (true) {
            System.out.println("Enter a number:");
            String input = scanner.nextLine();
            if (runnerE.processNumbers(input)) {
                break;
            }
        }
        // es.2
        runnerE.processFileReader("books.txt");
        // es.3
        while (true) {
            LOG.info("Enter the first number: ");
            String num1 = scanner.nextLine();
            LOG.info("Enter the second number: ");
            String num2 = scanner.nextLine();
            if (runnerE.processMultiCatch(num1, num2)) {
                break;
            }
        }
        // es.4
        runnerE.processTryWithResources("books.txt", "outputfile.txt");
        //es .5
        while (true) {
            LOG.info("Please enter a valid age: ");
            String input = scanner.nextLine();
            if (runnerE.processCustomException(input)) {
                break;
            }
        }
        // es.6
        while (true) {
            LOG.info("Enter an index 1: ");
            int index1 = scanner.nextInt();
            LOG.info("Enter an index 2: ");
            int index2 = scanner.nextInt();
            if (runnerE.processNestedTry(myList, index1, index2)) {
                break;
            }
        }
        // es.7
        while (true) {
            LOG.info("Enter a valid age: ");
            String input = scanner.nextLine();
            try {
                if (runnerE.processRethrowing(input)) {
                    break;
                }
            } catch (InvalidAgeException e) {
                LOG.error("Exception caught in main: {}", e.getMessage());
            }
        }
        // es.8
        // processSuppressedE("books.txt");
        runnerE.processWithSuppressed();

        scanner.close();


    }
}
