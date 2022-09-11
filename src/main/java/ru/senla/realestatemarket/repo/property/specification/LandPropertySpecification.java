package ru.senla.realestatemarket.repo.property.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

public class LandPropertySpecification {
    
    private LandPropertySpecification() {}


    public static Specification<LandProperty> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }
    
    public static Specification<LandProperty> hasUserIdOfOwner(Long userIdOfOwner) {
        return GenericPropertySpecification.hasUserIdOfOwner(userIdOfOwner);
    }
    
    public static Specification<LandProperty> hasIdAndUserIdOfOwner(Long id, Long userIdOfOwner) {
        return GenericPropertySpecification.hasIdAndUserIdOfOwner(id, userIdOfOwner);
    }
    
}
