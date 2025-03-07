package com.example;

import com.example.runner.TestProcessRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProcessRunnerTest {
    @Test
    void testIsNumberEven() {
        TestProcessRunner runnerT = new TestProcessRunner();
        
        assertTrue(runnerT.isNumberEven((2)));
        assertTrue(runnerT.isNumberEven((0)));
        assertTrue(runnerT.isNumberEven((-2)));

        assertFalse(runnerT.isNumberEven(3));
        assertFalse(runnerT.isNumberEven(7));
        assertFalse(runnerT.isNumberEven(-3));
    }
}
