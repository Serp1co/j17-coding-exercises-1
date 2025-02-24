package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;

// PROCESSING FILES
public class Book {
    private static final Logger logger = LoggerFactory.getLogger(Book.class);

    public static void main(String[] args) {

        // try with resources for close the stream
        // counts lines starting with 'I'
        try (Stream<String> lines = Files.lines(Path.of("books.txt"))) {

            long i = lines.filter(line -> line.startsWith("I"))
                    .count();
            logger.info("The count of lines starting with 'I' is: " + i);
        } catch (IOException e) {

            logger.error("Error reading file: ", e);
        }


        // Eliminate duplicates and filter by word length
        try (Stream<String> lines = Files.lines(Path.of("books.txt"))) {
            // create wordList
            List<String> wordsList = lines
                    .flatMap(line -> Stream.of(line.split("\\W+")))
                    .collect(Collectors.toList());
            logger.info("word list: {} ", wordsList);

            // eliminate duplicates with Set
            Set<String> wordSet = wordsList.stream()
                    .collect(Collectors.toSet());
            logger.info("eliminate duplicates: {}", wordSet);

            //filtered by length
            List<String> filteredByLength = wordsList.stream()
                    .filter(word -> word.length() >= 6)
                    .collect(Collectors.toList());
            logger.info("words with length >= 6: {}", filteredByLength);

            List<String> filteredBrandsByChar = wordsList.stream()
                    .filter(word -> word.startsWith("S"))
                    .collect(Collectors.toList());
            logger.info("brands starts with S: {}", filteredBrandsByChar);

        } catch (IOException e) {
            logger.error("Error reading file: ", e);
        }


    }
}
