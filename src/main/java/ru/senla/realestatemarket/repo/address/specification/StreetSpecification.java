package ru.senla.realestatemarket.repo.address.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;

import javax.persistence.criteria.Join;

public class StreetSpecification {

    private StreetSpecification() {}


    public static Specification<Street> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Street> hasRegionId(Long regionId) {
        return (root, query, criteriaBuilder) -> {
            Join<Street, City> cityJoin = root.join("city");
            Join<City, Region> regionJoin = cityJoin.join("region");
            return criteriaBuilder.equal(regionJoin.get("id"), regionId);
        };
    }

    public static Specification<Street> hasCityId(Long cityId) {
        return (root, query, criteriaBuilder) -> {
            Join<Street, City> cityJoin = root.join("city");
            return criteriaBuilder.equal(cityJoin.get("id"), cityId);
        };
    }

}
