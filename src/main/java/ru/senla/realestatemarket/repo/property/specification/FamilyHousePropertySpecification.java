package ru.senla.realestatemarket.repo.property.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import javax.persistence.criteria.Join;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class FamilyHousePropertySpecification {
    
    private FamilyHousePropertySpecification() {}


    public static Specification<FamilyHouseProperty> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }
    
    public static Specification<FamilyHouseProperty> hasUserIdOfOwner(Long userIdOfOwner) {
        return GenericPropertySpecification.hasUserIdOfOwner(userIdOfOwner);
    }
    
    public static Specification<FamilyHouseProperty> hasIdAndUserIdOfOwner(Long id, Long userIdOfOwner) {
        return GenericPropertySpecification.hasIdAndUserIdOfOwner(id, userIdOfOwner);
    }
    
    public static Specification<FamilyHouseProperty> hasFamilyHouseId(Long familyHouseId) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseProperty, FamilyHouse> houseJoin = root.join("familyHouse");
            return criteriaBuilder.equal(houseJoin.get("id"), familyHouseId);
        };
    }

}
