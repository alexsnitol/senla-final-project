package ru.senla.realestatemarket.repo.property.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import javax.persistence.criteria.Join;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class ApartmentPropertySpecification {

    private ApartmentPropertySpecification() {}


    public static Specification<ApartmentProperty> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }

    public static Specification<ApartmentProperty> hasUserIdOfOwner(Long userIdOfOwner) {
        return GenericPropertySpecification.hasUserIdOfOwner(userIdOfOwner);
    }

    public static Specification<ApartmentProperty> hasIdAndUserIdOfOwner(Long id, Long userIdOfOwner) {
        return GenericPropertySpecification.hasIdAndUserIdOfOwner(id, userIdOfOwner);
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
