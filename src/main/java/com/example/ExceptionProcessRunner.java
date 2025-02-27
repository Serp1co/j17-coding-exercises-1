package com.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ExceptionProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcessRunner.class);

    public static void main(String[] args) {
        logger.info("Exception process start");
        Scanner scanner = new Scanner(System.in);
        processNumbers(scanner);
        processMultiCatch(scanner);
        processCustomException(scanner);
        processFileReader();
        processTryWithResources();

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
        // try-with-resource not use finally or scanner.close()

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
}
