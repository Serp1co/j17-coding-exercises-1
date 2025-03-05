package com.example;


import java.io.IOException;
import java.util.Scanner;

import static com.example.ExceptionProcessRunner.*;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // es.1
        while (true) {
            System.out.println("Enter a number:");
            String input = scanner.nextLine();
            if (processNumbers(input)) {
                break;
            }
        }
        // es.2
        processFileReader("books.txt");
        // es.3
        while (true) {
            System.out.print("Enter the first number: ");
            String num1 = scanner.nextLine();
            System.out.print("Enter the second number: ");
            String num2 = scanner.nextLine();
            if (processMultiCatch(num1, num2)) {
                break;
            }
        }
        // es.4
        processTryWithResources("books.txt", "outputfile.txt");
        //es .5
        while (true) {
            System.out.println("Please enter a valid age: ");
            String input = scanner.nextLine();
            if (processCustomException(input)) {
                break;
            }
        }
        // es.6
        //create list
        int[] myList = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        while (true) {
            System.out.print("Enter an index 1: ");
            int index1 = scanner.nextInt();
            System.out.print("Enter an index 2: ");
            int index2 = scanner.nextInt();
            if (processNestedTry(myList, index1, index2)) {
                break;
            }
        }
        // es.7
        while (true) {
            System.out.println("Enter a valid age: ");
            String input = scanner.nextLine();
            try {
                if (processRethrowing(input)) {
                    break;
                }
            } catch (InvalidAgeException e) {
                System.out.println("Exception caught in main: " + e.getMessage());
            }
        }
        // es.8
        // processSuppressedE("books.txt");
        processWithSuppressed();


        scanner.close();


    }
}
