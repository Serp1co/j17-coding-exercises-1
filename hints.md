# Hints

Below are **optional** hints for each category of exercises. They offer **general guidance** without revealing full solutions.

---

## Streams Exercises

1. **Basic Filtering and Mapping**  
   - Consider methods like `filter()`, `map()`, and `forEach()`.  
   - Look for ways to select certain elements (e.g., even/odd checks) and transform their values.

2. **Summation and Reduction**  
   - Explore `mapToInt(...).sum()` or `reduce(...)` for arithmetic operations.  
   - Filter out certain elements before applying the summation or product.

3. **Collecting into Different Data Structures**  
   - Use collectors like `Collectors.toList()` or `Collectors.toSet()`.  
   - Filter or transform elements before collecting.

4. **Grouping and Partitioning**  
   - Check out `Collectors.groupingBy()` to group by a property (e.g., department).  
   - Use `Collectors.partitioningBy()` for boolean-based splits.

5. **FlatMap Practice with Nested Collections**  
   - Use `flatMap()` to flatten nested lists or arrays into a single stream.  
   - Then apply filters, sorting, or other operations on the flattened elements.

6. **Reading and Processing a File with Streams**  
   - Use `Files.lines(...)` to read lines from a file.  
   - Split each line into tokens, flatten (e.g., via `flatMap()`), and filter or transform.

7. **Custom Collector**  
   - Explore creating a custom `Collector` via `Collector.of(...)`.  
   - Think about how to accumulate results (e.g., min and max) in one pass.

8. **Parallel Streams**  
   - Convert to parallel with `.parallelStream()`.  
   - Consider CPU-bound tasks (e.g., prime-checking) for potential performance benefits.

---

## Exception Handling Exercises

1. **Basic `try-catch`**  
   - Use a `try` block to handle potential errors, and a `catch` block to manage fallback or logging.  
   - Return a default value if parsing or processing fails.

2. **Using `throws` in Method Signatures**  
   - Allow an exception (e.g., `IOException`) to bubble up by declaring it in your method signature.  
   - The caller then handles or re-throws it.

3. **Multiple Exceptions in One `catch`**  
   - Use syntax like `catch (ExceptionA | ExceptionB e)`.  
   - Decide if you need special handling based on exception type or a common response.

4. **Try-with-Resources**  
   - Use `try (Resource r = ...) { ... }` to ensure automatic resource closing.  
   - Useful for working with files, streams, or database connections.

5. **Creating Custom Exceptions**  
   - Extend `Exception` or `RuntimeException`.  
   - Throw it with a clear message when special error conditions occur (e.g., invalid data).

6. **Nested `try-catch` vs. Separate Methods**  
   - Decide if you should catch exceptions right away or let them propagate to be handled at a higher level.  
   - Weigh clarity vs. convenience.

7. **Throwing and Re-throwing Exceptions**  
   - Catch a lower-level exception (e.g., `IllegalArgumentException`) and re-throw it as a custom exception (e.g., `MyAppException`) to add context.

8. **Suppressed Exceptions (Advanced)**  
   - When closing multiple resources manually, consider adding exceptions to the main exception’s suppressed list if multiple failures occur.

---

## Unit Testing Exercises

1. **Setup and Teardown**  
   - Use JUnit annotations like `@BeforeEach` or `@AfterEach` if needed.  
   - `@TempDir` can help with file-based tests.

2. **Positive (Happy Path) Tests**  
   - Verify correct outputs using `assertEquals(...)`, `assertTrue(...)`, or other assertions.  
   - Ensure you test typical valid input scenarios.

3. **Negative (Error) Tests**  
   - Use `assertThrows(...)` to check if an exception is thrown correctly.  
   - Verify the error messages or exception types.

4. **Boundary Cases**  
   - Include edge inputs (e.g., empty lists, zero/negative numbers) to ensure resilience.  
   - Check behavior around limits (e.g., max/min values).

5. **Assertions**  
   - Beyond `assertEquals`, explore `assertAll`, `assertArrayEquals`, etc.  
   - Group multiple related checks in a single test for clarity.

6. **Refactoring to Testability**  
   - Separate logic from I/O or external services to make testing easier.  
   - Use smaller methods with single responsibilities to simplify testing.
