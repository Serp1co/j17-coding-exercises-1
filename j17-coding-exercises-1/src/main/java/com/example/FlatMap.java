package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMap {

    private static final Logger logger = LoggerFactory.getLogger(FlatMap.class);

    public static void main(String[] args) {

        List<String> Marvel = Arrays.asList("Hulk", "Spiderman", "Thor", "Ironman");
        List<String> DC = Arrays.asList("Wwoman", "Flash", "Superman", "Batman");
        List<String> WSJ = Arrays.asList("Naruto", "Luffy", "Saitama", "Goku");

        // combining the hero's lists
        List<List<String>> listOfLists = Arrays.asList(Marvel, DC, WSJ);
        logger.info("before flatMap: {}", listOfLists);

        // flat map
        List<String> listofHeroes = listOfLists.stream()
                .flatMap(list -> list.stream())
                .sorted() // order
                .collect(Collectors.toList());
        logger.info("after flatMap: {}", listofHeroes);


    }
}


