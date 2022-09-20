package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification.hasId;
import static ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification.hasStatus;
import static ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification.hasUserIdOfOwnerInProperty;

@Slf4j
@Repository
public class ApartmentAnnouncementRepositoryImpl
        extends AbstractHousingAnnouncementRepositoryImpl<ApartmentAnnouncement>
        implements IApartmentAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncement.class);
    }


    @Override
    public List<ApartmentAnnouncement> findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(
            List<String> searchKeys
    ) {
        return findAllInTheTextFieldsOfWhichContainsTheKeys(hasStatus(AnnouncementStatusEnum.OPEN), searchKeys);
    }

    @Override
    public List<ApartmentAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys) {
        return findAllInTheTextFieldsOfWhichContainsTheKeys(null, searchKeys);
    }

    private List<ApartmentAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(
            @Nullable Specification<ApartmentAnnouncement> specification, List<String> searchKeys
    ) {
        CriteriaQuery<ApartmentAnnouncement> criteriaQuery = criteriaBuilder.createQuery(ApartmentAnnouncement.class);

        Root<ApartmentAnnouncement> announcementRoot = criteriaQuery.from(ApartmentAnnouncement.class);

        Join<ApartmentAnnouncement, ApartmentProperty> propertyJoin = announcementRoot.join("property");
        Join<ApartmentProperty, ApartmentHouse> houseJoin = propertyJoin.join("apartmentHouse");
        Join<ApartmentHouse, Address> addressJoin = houseJoin.join("address");
        Join<Address, Street> streetJoin = addressJoin.join("street");
        Join<Street, City> cityJoin = streetJoin.join("city");
        Join<City, Region> regionJoin = cityJoin.join("region");

        List<Predicate> likePredicates = new LinkedList<>();
        for (String key : searchKeys) {
            likePredicates.add(
                    criteriaBuilder.like(announcementRoot.get("description"), "%" + key + "%")
            );

            likePredicates.add(
                    criteriaBuilder.like(propertyJoin.get("apartmentNumber"), "%" + key + "%")
            );

            likePredicates.add(
                    criteriaBuilder.like(addressJoin.get("houseNumber"), "%" + key + "%")
            );

            likePredicates.add(
                    criteriaBuilder.like(streetJoin.get("name"), "%" + key + "%")
            );

            likePredicates.add(
                    criteriaBuilder.like(cityJoin.get("name"), "%" + key + "%")
            );

            likePredicates.add(
                    criteriaBuilder.like(regionJoin.get("name"), "%" + key + "%")
            );
        }

        return findAllBySpecificationAndLikePredicates(specification, likePredicates, criteriaQuery, announcementRoot);
    }

    @Override
    public ApartmentAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner) {
        return findOne(hasId(id)
                .and(hasUserIdOfOwnerInProperty(userIdOfOwner))
        );
    }

    @Override
    public ApartmentAnnouncement findByIdWithStatus(Long id, AnnouncementStatusEnum status) {
        return findOne(hasId(id)
                .and(hasStatus(status))
        );
    }

}
