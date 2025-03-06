package com.example.runner;

import com.example.exceptions.InvalidAgeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class ExceptionProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcessRunner.class);

    public boolean processNumbers(String input) {
        try {
            Integer.parseInt(input);
            logger.info("you entered!");
            return true;

        } catch (NumberFormatException e) {
            logger.error("NumberFormatException occurred, number is not valid.");
            return false;
        }

    }

    public void processFileReader(String filePath) {
        // try-with-resource not use finally or scanner.close()
        try (InputStream inputStream = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                logger.error("File not found: {}", filePath);
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String> wordList = reader.lines().sorted().toList();
                logger.info("Books Word list: {}", wordList);
            }
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }
    }

    public boolean processMultiCatch(String num1Str, String num2Str) {
        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
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
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers.");
            return false;
        } catch (ArithmeticException e) {
            logger.error("ArithmeticException {}", e.getMessage());
            return false;
        }

    }

    public void processTryWithResources(String inputFile, String outputFile) {
        try (InputStream inputStream = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream(inputFile)) {
            if (inputStream == null) {
                logger.error("inputFile not found: {}", inputFile);
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
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

    public boolean processCustomException(String input) {
        try {
            int age = Integer.parseInt(input);
            if (age < 0) {
                throw new InvalidAgeException("Age cannot be negative", age);
            }
            if (age < 18 || age > 99) {
                throw new InvalidAgeException("Error, Please enter a valid age", age);
            }
            logger.info("Valid age entered: {}", age);
            return true;

        } catch (NumberFormatException e) {
            logger.error("NumberFormatException occurred: {}", e.getMessage());
            System.out.println("Invalid input. Please enter a valid age.");
            return false;
        } catch (InvalidAgeException e) {
            logger.error("InvalidAgeException: {}", e.getMessage());
            return false;
        }

    }

    public boolean processNestedTry(int[] list, int index1, int index2) {
        // nested instead multi blocks of catch
        try {
            if (index1 >= list.length || index1 < 0) {
                throw new ArrayIndexOutOfBoundsException("Index1 out of bounds.");
            }

            System.out.println("Element at index " + index1 + ": " + list[index1]);
            try {
                if (index2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero.");
                }
                int result = list[index1] / index2;
                System.out.println("Result of division: " + result);
                return true;
            } catch (ArithmeticException e) {
                logger.error("ArithmeticException: {}", e.getMessage(), e);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("ArrayIndexOutOfBoundsException message: {}", e.getMessage());
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException message: ", e);
        }
        return false;
    }

    public boolean processRethrowing(String input) {
        try {
            int age = Integer.parseInt(input);
            if (age < 0) {
                throw new InvalidAgeException("Age cannot be negative", age);
            }
            if (age < 18 || age > 99) {
                throw new InvalidAgeException("Invalid age", age);
            }
            logger.info("Valid age: {}", age);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid age.");
            return false;
        } catch (InvalidAgeException e) {
            logger.error("InvalidAgeException message: {}", e.getMessage());
            throw e; // handle in main
        }
    }

    // basically used in try-with-resources
    static void processSuppressedE(String filePath) throws IOException {
        // Open file
        try (InputStream fileIn = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream(filePath)) {
            if (fileIn == null) {
                throw new FileNotFoundException("File not found in resources!");
            }
            //simulate exception
            throw new IOException("Simulated exception while processing file");

        } catch (IOException e) {
            logger.error("Main IOException message: {}", e.getMessage());
            throw e;  // rethrowing
        }
    }

    public void processWithSuppressed() throws IOException {
        IOException mainException = new IOException("Main error occurred");

        try {
            throw new IOException("Secondary error during cleanup");
        } catch (IOException suppressedException) {
            mainException.addSuppressed(suppressedException);
        }
        throw mainException;
    }
}
