package com.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ExceptionProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcessRunner.class);

    public static void main(String[] args) throws IOException {
        logger.info("Exception process start");
        Scanner scanner = new Scanner(System.in);
        processNumbers(scanner);
        processMultiCatch(scanner);
        processCustomException(scanner);
        processFileReader();
        processTryWithResources();
        processNestedTry();
        try {
            processRethrowing(scanner);
        } catch (InvalidAgeException e) {
            System.out.println("Exception caught in main: " + e.getMessage());
        }
        // processSuppressedE();
        processWithSuppressed();

        scanner.close();
    }

    private static void processNumbers(Scanner scanner) {
        // use scanner main
        while (true) {
            System.out.println("Please digit a valid number: ");

            try {
                double number = Integer.parseInt(scanner.nextLine());
                logger.info("you entered!");
                break;

            } catch (NumberFormatException e) {
                logger.error("NumberFormatException occurred, number is not valid.");
                System.out.println("Invalid input. Please enter valid integers.");
            }
        }
    }

    private static void processFileReader() {
        // try-with-resource not use finally or scanner.close()
        try (InputStream inputStream = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream("books.txt")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String> wordList = reader.lines().sorted().toList();
                logger.info("Books Word list: {}", wordList);
            }
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }
    }

    private static void processMultiCatch(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter the first number: ");
                double num1 = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter the second number: ");
                double num2 = Integer.parseInt(scanner.nextLine());

                //sum
                double sum = num1 + num2;
                logger.info("The sum of {} and {} is: {}", num1, num2, sum);

                // double division per 0 = infinity
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero! Please enter a valid divisor.");
                }
                //division
                double division = num1 / num2;
                logger.info("The division of {} and {} is: {}", num1, num2, division);

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers.");
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static void processTryWithResources() {
        try (InputStream inputStream = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream("books.txt")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("outputfile.txt"))) {

                String text;
                while ((text = reader.readLine()) != null) {
                    writer.write(text);
                    writer.newLine();
                }
                logger.info("File is successfully copied to outputfile.txt");
            }
        } catch (IOException e) {
            logger.error("Error reading or writing file: ", e);
        }
        logger.info("Resources are closed and content has been written into outputfile.txt");
    }

    private static void processCustomException(Scanner scanner) {
        while (true) {
            System.out.println("Please enter a valid age: ");
            try {
                int age = Integer.parseInt(scanner.next());
                if (age < 0) {
                    throw new InvalidAgeException("Age cannot be negative: " + age);
                }
                if (age < 18 || age > 99) {
                    throw new InvalidAgeException("Error, Please enter a valid age: " + age);
                }
                logger.info("Valid age entered: {}", age);
                break;
            } catch (NumberFormatException e) {
                logger.error("NumberFormatException occurred: " + e.getMessage());
                System.out.println("Invalid input. Please enter a valid age.");
            } catch (InvalidAgeException e) {
                logger.error("InvalidAgeException: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private static void processNestedTry() {
        // nested instead multi blocks of catch
        try {
            int[] list = {1, 2, 3, 4, 5, 6, 7, 8};
            System.out.println(list[5]);
            try {
                int x = list[12] / 0;
            } catch (ArithmeticException e) {
                System.out.println("Cannot divide by zero.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element at such index does not exists");
        }
    }

    private static void processRethrowing(Scanner scanner) {
        while (true) {
            System.out.println("Please enter a valid age: ");
            try {
                int age = Integer.parseInt(scanner.next());
                if (age < 0) {
                    throw new InvalidAgeException("Age cannot be negative: " + age);
                }
                if (age < 18 || age > 99) {
                    throw new InvalidAgeException("Error, Please enter a valid age: " + age);
                }
                logger.info("Valid age entered: {}", age);
                break;
            } catch (NumberFormatException e) {
                logger.error("NumberFormatException occurred: " + e.getMessage());
                System.out.println("Invalid input. Please enter a valid age.");
            } catch (InvalidAgeException e) {
                logger.error("InvalidAgeException: " + e.getMessage());
                throw e; // handle in main
            }
        }
    }

    // basically used in try-with-resources
    private static void processSuppressedE() throws IOException {
        Throwable firstException = null;

        // Open file
        try (InputStream fileIn = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream("Books.txt")) {
            if (fileIn == null) {
                throw new FileNotFoundException("File not found in resources!");
            }
            //simulate exception
            throw new IOException("Simulated exception while processing file");

        } catch (IOException e) {
            logger.error("Main exception: {}", e.getMessage());
            firstException = e;
            throw e;  // rethrowing
        }
    }

    private static void processWithSuppressed() throws IOException {
        IOException mainException = new IOException("Main error occurred");

        try {
            throw new IOException("Secondary error during cleanup");
        } catch (IOException suppressedException) {
            mainException.addSuppressed(suppressedException);
        }

        throw mainException;
    }
}
