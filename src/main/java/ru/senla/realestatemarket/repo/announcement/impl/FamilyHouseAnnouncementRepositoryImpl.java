package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement.specification.FamilyHouseAnnouncementSpecification.hasId;
import static ru.senla.realestatemarket.repo.announcement.specification.FamilyHouseAnnouncementSpecification.hasUserIdOfOwnerInProperty;

@Slf4j
@Repository
public class FamilyHouseAnnouncementRepositoryImpl
        extends AbstractHousingAnnouncementRepositoryImpl<FamilyHouseAnnouncement>
        implements IFamilyHouseAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncement.class);
    }

    
    @Override
    public List<FamilyHouseAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys) {
        CriteriaQuery<FamilyHouseAnnouncement> criteriaQuery
                = criteriaBuilder.createQuery(FamilyHouseAnnouncement.class);

        Root<FamilyHouseAnnouncement> announcementRoot = criteriaQuery.from(FamilyHouseAnnouncement.class);

        Join<FamilyHouseAnnouncement, FamilyHouseProperty> propertyJoin = announcementRoot.join("property");
        Join<FamilyHouseProperty, FamilyHouse> houseJoin = propertyJoin.join("familyHouse");
        Join<FamilyHouse, Address> addressJoin = houseJoin.join("address");
        Join<Address, Street> streetJoin = addressJoin.join("street");
        Join<Street, City> cityJoin = streetJoin.join("city");
        Join<City, Region> regionJoin = cityJoin.join("region");

        List<Predicate> likePredicates = new LinkedList<>();
        for (String key : searchKeys) {
            likePredicates.add(
                    criteriaBuilder.like(announcementRoot.get("description"), "%" + key + "%")
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

        criteriaQuery
                .select(announcementRoot)
                .distinct(true)
                .where(
                        criteriaBuilder.or(likePredicates.toArray(new Predicate[0]))
                );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public FamilyHouseAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner) {
        return findOne(hasId(id)
                .and(hasUserIdOfOwnerInProperty(userIdOfOwner)));
    }

}
