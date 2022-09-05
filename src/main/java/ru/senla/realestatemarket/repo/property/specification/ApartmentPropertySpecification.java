package ru.senla.realestatemarket.repo.property.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

import javax.persistence.criteria.Join;

public class ApartmentPropertySpecification extends GenericPropertySpecification {

    private ApartmentPropertySpecification() {
        super();
    }


    public static Specification<ApartmentProperty> hasApartmentHouseId(Long apartmentHouseId) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentProperty, ApartmentHouse> houseJoin = root.join("apartmentHouse");
            return criteriaBuilder.equal(houseJoin.get("id"), apartmentHouseId);
        };
    }

    public static Specification<ApartmentProperty> hasApartmentNumber(String apartmentNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("apartmentNumber"), apartmentNumber);
    }

}
