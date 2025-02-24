# j17-coding-exercises-1

Java 17 Exercises

This repository contains three categories of exercises:

1. [Streams Exercises](#streams-exercises)  
2. [Exception Handling Exercises](#exception-handling-exercises)  
3. [Unit Testing Exercises](#unit-testing-exercises)

Use these exercises to practice Java 17 features and best practices. No sample code or hints are provided here—try to solve them on your own or with guidance in a mentoring session!

---

## Streams Exercises

1. **Basic Filtering and Mapping**  
   - Create a list of integers. Filter out even numbers, then multiply the remaining odd numbers by a factor. Print or store the results.

2. **Summation and Reduction**  
   - From a list of integers, calculate the sum of even numbers and the product of odd numbers using stream operations.

3. **Collecting into Different Data Structures**  
   - Given a list of strings, collect subsets into a `List`, `Set`, or other collections based on certain conditions (e.g., string length, starting character).

4. **Grouping and Partitioning**  
   - Given a list of objects (e.g., employees), group them by a specific field (e.g., department) and compute statistics (like counts or averages). Also, partition them into two categories based on a condition.

5. **FlatMap Practice with Nested Collections**  
   - Flatten a list of lists into a single list or stream, filter based on certain criteria, and process the data (e.g., sorting, collecting).

6. **Reading and Processing a File with Streams**  
   - Read lines from a file and split each line into words. Filter, transform, or collect these words as needed (e.g., remove duplicates or filter by length).

7. **Custom Collector**  
   - Implement a custom collector that processes a stream of numbers (or objects) in a single pass, storing specific results (e.g., min and max).

8. **Parallel Streams**  
   - Create a large dataset and process it in parallel (e.g., filter primes). Compare performance or correctness against sequential streams.

---

## Exception Handling Exercises

1. **Basic `try-catch`**  
   - Write a method that attempts to parse an integer from a string and handles `NumberFormatException`, returning a fallback value on failure.

2. **Using `throws` in Method Signatures**  
   - Create a method that reads file content and declares it may throw `IOException`. Handle the exception in the caller.

3. **Multiple Exceptions in One `catch` (Multi-catch)**  
   - Implement a method that can throw different exceptions (e.g., `ArithmeticException`, `NumberFormatException`) and use a single catch block to handle both.

4. **Try-with-Resources**  
   - Copy the contents of one file to another using a `try-with-resources` block to ensure resources are properly closed.

5. **Creating Custom Exceptions**  
   - Create a custom exception (e.g., `InvalidAgeException`) and throw it under certain conditions (e.g., out-of-range age). Catch and display a custom error message.

6. **Nested `try-catch` vs. Separate Methods**  
   - Experiment with handling exceptions immediately vs. letting them propagate to a higher-level method.

7. **Throwing and Re-throwing Exceptions**  
   - Validate some data in one method, catch the exception, and re-throw it as a different custom exception to add context.

8. **Suppressed Exceptions (Advanced)**  
   - Manually manage closing multiple resources, capturing any exceptions thrown when closing each resource, and then combine them (suppress) before re-throwing.

---

## Unit Testing Exercises

Use a testing framework such as **JUnit 5** to verify that your implementations for the above exercises work as intended. For each exercise:

1. **Setup and Teardown**  
   - Organize tests so that you can create and clean up necessary resources (e.g., temporary files).

2. **Positive (Happy Path) Tests**  
   - Verify that expected inputs produce the correct results (e.g., correct stream processing, valid file reads).

3. **Negative (Error) Tests**  
   - Confirm that exceptions are thrown when invalid conditions occur (e.g., invalid input parsing, custom exception triggers).

4. **Boundary Cases**  
   - Include edge cases (e.g., empty lists, boundary values for arithmetic, file not found).

5. **Assertions**  
   - Use methods like `assertEquals`, `assertTrue`, `assertThrows`, and others to confirm behavior.

6. **Refactoring to Testability**  
   - If an exercise is hard to test, refactor your code to make it more modular (e.g., separate logic from I/O operations).

---

## Need Some Guidance?

Check out the [hints](./hints.md) file for optional tips and pointers on each exercise.

**Happy Coding!**  
Explore these exercises at your own pace, and feel free to expand or adapt them based on what you need to learn or teach.
