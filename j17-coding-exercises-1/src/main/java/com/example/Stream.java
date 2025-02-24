package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map;

import java.util.stream.Collectors;


public class Stream {
    private static final Logger logger = LoggerFactory.getLogger(Stream.class);

    public static void main(String[] args) {
        logger.info("Streams exercises start");

        // es.1
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(10);
        logger.info("numbers: {}", numbers);

        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        logger.info("Even numbers: {}", evenNumbers);

        List<Integer> oddNumbers = numbers.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * 3)
                .collect(Collectors.toList());
        logger.info("Odd numbers * 3: {}", oddNumbers);


        logger.info("\n=====================\n");


        // es.2
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers2 = numbers2.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        Integer sum = evenNumbers2.stream()
                .reduce(0, (a, b) -> a + b);
        logger.info("sum even numbers: {}", sum);

        List<Integer> oddNumbers2 = numbers2.stream()
                .filter(n -> n % 2 != 0)
                .collect(Collectors.toList());
        Integer product = oddNumbers2.stream()
                .reduce(1, (a, b) -> a * b);
        logger.info("product odd numbers: {}", product);


        logger.info("\n=====================\n");


        // es.3
        List<String> carBrands = Arrays.asList(
                "Toyota",
                "Ford",
                "BMW",
                "Mercedes",
                "Audi",
                "Honda",
                "Volkswagen",
                "Chevrolet",
                "Nissan",
                "Porsche",
                "Fiat",
                "Smart",
                "Audi A1",
                "Audi A2",
                "Audi A3"
        );
        logger.info("car brands: {}", carBrands);

        // filtered by length
        List<String> filteredBrandsByLength = carBrands.stream()
                .filter(brand -> brand.length() <= 6)
                .collect(Collectors.toList());
        logger.info("brands filtered by length: {}", filteredBrandsByLength);

        // filtered by char
        List<String> filteredBrandsByCharA = carBrands.stream()
                .filter(brand -> brand.startsWith("A"))
                .collect(Collectors.toList());
        logger.info("brands starts with A: {}", filteredBrandsByCharA);


        // set filtered by char
        Set<String> filteredBrandsSet = carBrands.stream()
                .filter(brand -> brand.startsWith("Aud") || brand.startsWith("F"))
                .collect(Collectors.toSet());
        System.out.println("Brands starting with 'Aud' and 'F': " + filteredBrandsSet);

        // set filtered by brands name
        Set<String> filteredBrandsAudi = carBrands.stream()
                .filter(brand -> brand.equals("Audi"))
                .collect(Collectors.toSet());
        System.out.println("Audi: " + filteredBrandsAudi);


        // map filtered by prefix
        List<String> prefixes = Arrays.asList("A", "F", "B");

        Map<String, List<String>> groupedBrands = carBrands.stream()
                .filter(brand -> prefixes.stream().anyMatch(prefix -> brand.startsWith(prefix)))
                .collect(Collectors.groupingBy(brand -> prefixes.stream().filter(brand::startsWith).findFirst().get()));
        groupedBrands.forEach((prefix, brands) ->
                System.out.println("Brands starting with '" + prefix + "': " + brands));


        logger.info("\n=====================\n");
        
    }
}
