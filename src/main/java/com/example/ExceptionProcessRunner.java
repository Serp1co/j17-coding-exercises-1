package com.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ExceptionProcessRunner {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcessRunner.class);

    public static void main(String[] args) {
        logger.info("Exception process start");
        processNumbers();

    }

    private static void processNumbers() {
        int number;
        Scanner scanner1 = new Scanner(System.in);

        while (true) {
            System.out.println("Please digit a valid number: ");

            try {
                number = Integer.parseInt(scanner1.next());
                logger.info(" valid : {}", number);
                break;

            } catch (NumberFormatException e) {
                logger.error("NumberFormatException occurred, number is not valid.");
            }
        }
    }
}
