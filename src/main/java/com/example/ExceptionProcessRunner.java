package com.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class ExceptionProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcessRunner.class);

    public static void main(String[] args) {
        logger.info("Exception process start");
        processNumbers();
        processFileReader();

    }

    private static void processNumbers() {
        int number;
        Scanner scanner1 = new Scanner(System.in);

        while (true) {
            System.out.println("Please digit a valid number: ");

            try {
                number = Integer.parseInt(scanner1.next());
                logger.info("you entered!");
                break;

            } catch (NumberFormatException e) {
                logger.error("NumberFormatException occurred, number is not valid.");
            }
        }
    }

    private static void processFileReader() {
        try (InputStream inputStream = ExceptionProcessRunner.class.getClassLoader().getResourceAsStream("books.txt")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String> wordList = reader.lines().toList();
                logger.info("Books Word list: {}", wordList);
            }
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }
    }
}
