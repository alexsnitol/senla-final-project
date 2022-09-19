package ru.senla.realestatemarket.repo.timetable.specification;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class GenericTimetableSpecification {

    private GenericTimetableSpecification() {}


    public static  <T> Specification<T> concernsTheIntervalBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to
    ) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.between(root.get("fromDt"), from, to),
                        criteriaBuilder.between(root.get("toDt"), from, to),
                        criteriaBuilder.and(
                                criteriaBuilder.lessThanOrEqualTo(root.get("fromDt"), from),
                                criteriaBuilder.greaterThanOrEqualTo(root.get("toDt"), to)
                        )
                );
    }

    public static <T> Specification<T> concernsTheIntervalBetweenSpecificFromAndToExcludingIntervalItself(
            LocalDateTime from, LocalDateTime to
    ) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get("fromDt"), from),
                                criteriaBuilder.lessThan(root.get("fromDt"), to)
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get("toDt"), from),
                                criteriaBuilder.lessThan(root.get("toDt"), to)
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.lessThan(root.get("fromDt"), from),
                                criteriaBuilder.greaterThan(root.get("toDt"), to)
                        )
                );
    }

    public static <T> Specification<T> intervalWithSpecificFromAndTo(LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("fromDt"), from),
                            criteriaBuilder.equal(root.get("toDt"), to)
                    );
    }

}
