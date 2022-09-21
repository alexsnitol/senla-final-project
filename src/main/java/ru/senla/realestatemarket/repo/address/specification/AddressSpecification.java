package ru.senla.realestatemarket.repo.address.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;

import javax.persistence.criteria.Join;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class AddressSpecification {
    
    private AddressSpecification() {}

    
    public static Specification<Address> hasRegionId(Long regionId) {
        return (root, query, criteriaBuilder) -> {
            Join<Address, Street> streetJoin = root.join("street");
            Join<Street, City> cityJoin = streetJoin.join("city");
            Join<City, Region> regionJoin = cityJoin.join("region");
            return criteriaBuilder.equal(regionJoin.get("id"), regionId);
        };
    }

    public static Specification<Address> hasCityId(Long cityId) {
        return (root, query, criteriaBuilder) -> {
            Join<Address, Street> streetJoin = root.join("street");
            Join<Street, City> cityJoin = streetJoin.join("city");
            return criteriaBuilder.equal(cityJoin.get("id"), cityId);
        };
    }

    public static Specification<Address> hasStreetId(Long streetId) {
        return (root, query, criteriaBuilder) -> {
            Join<Address, Street> streetJoin = root.join("street");
            return criteriaBuilder.equal(streetJoin.get("id"), streetId);
        };
    }

    public static Specification<Address> hasHouseNumber(String houseNumber) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("houseNumber"), houseNumber));
    }
    
}
