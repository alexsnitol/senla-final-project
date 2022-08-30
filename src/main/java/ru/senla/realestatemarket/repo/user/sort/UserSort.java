package ru.senla.realestatemarket.repo.user.sort;

import org.springframework.data.domain.Sort;

import java.util.List;

public class UserSort {

    private UserSort() {}


    public static Sort byAlphabetically(Sort.Direction direction) {
        return Sort.by(List.of(
                new Sort.Order(direction, "firstName"),
                new Sort.Order(direction, "lastName"),
                new Sort.Order(direction, "patronymic")
        ));
    }

}
