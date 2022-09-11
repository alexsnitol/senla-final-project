package ru.senla.realestatemarket.repo.dictionary.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

public class AnnouncementTopPriceSpecification {

    private AnnouncementTopPriceSpecification() {}


    public static Specification<AnnouncementTopPrice> hasPropertyType(PropertyTypeEnum propertyType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("propertyType"), propertyType);
    }

    public static Specification<AnnouncementTopPrice> hasAnnouncementType(AnnouncementTypeEnum announcementType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("announcementType"), announcementType);
    }

}
