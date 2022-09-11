package ru.senla.realestatemarket.repo.timetable.specification;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class GenericTimetableSpecification {

    private GenericTimetableSpecification() {}


    public static  <T> Specification<T> fromDtOrToDtMustBeBetweenSpecificFromAndTo(LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.between(root.get("fromDt"), from, to),
                        criteriaBuilder.between(root.get("toDt"), from, to)
                );
    }

}
