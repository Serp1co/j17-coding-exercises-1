package com.example.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestProcessRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TestProcessRunner.class);

    public boolean isNumberEven(Integer input) {
        try {
            return input % 2 == 0;
        } catch (NumberFormatException e) {
            LOG.error("Error{}", input);
            return false;
        }

    }
}
