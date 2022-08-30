package ru.senla.realestatemarket.repo.address.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;

import javax.persistence.criteria.Join;

public class CitySpecification {

    private CitySpecification() {}


    public static Specification<City> hasId(Long cityId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), cityId);
    }

    public static Specification<City> hasRegionId(Long regionId) {
        return (root, query, criteriaBuilder) -> {
            Join<City, Region> regionJoin = root.join("region");
            return criteriaBuilder.equal(regionJoin.get("id"), regionId);
        };
    }

}
