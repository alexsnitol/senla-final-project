package ru.senla.realestatemarket.repo.specification;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification {

    private GenericSpecification() {}


    public static <T, I> Specification<T> hasId(I id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }


}
