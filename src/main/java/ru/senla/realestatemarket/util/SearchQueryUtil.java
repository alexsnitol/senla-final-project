package ru.senla.realestatemarket.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchQueryUtil {

    private SearchQueryUtil() {}

    public static List<String> getKeyWordsSpit(String keyWords) {
        return Arrays.stream(keyWords.split(",")).collect(Collectors.toList());
    }

}
