package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification.hasId;
import static ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification.hasStatus;
import static ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification.hasUserIdOfOwnerInProperty;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class LandAnnouncementRepositoryImpl
        extends AbstractAnnouncementRepositoryImpl<LandAnnouncement>
        implements ILandAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(LandAnnouncement.class);
    }


    @Override
    public List<LandAnnouncement> findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys) {
        return findAllInTheTextFieldsOfWhichContainsTheKeys(hasStatus(AnnouncementStatusEnum.OPEN), searchKeys);
    }

    @Override
    public List<LandAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys) {
        return findAllInTheTextFieldsOfWhichContainsTheKeys(null, searchKeys);
    }

    private List<LandAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(
            @Nullable Specification<LandAnnouncement> specification, List<String> searchKeys
    ) {
        CriteriaQuery<LandAnnouncement> criteriaQuery = criteriaBuilder.createQuery(LandAnnouncement.class);

        Root<LandAnnouncement> announcementRoot = criteriaQuery.from(LandAnnouncement.class);

        Join<LandAnnouncement, LandProperty> propertyJoin = announcementRoot.join("property");
        Join<Address, Street> streetJoin = propertyJoin.join("street");
        Join<Street, City> cityJoin = streetJoin.join("city");
        Join<City, Region> regionJoin = cityJoin.join("region");

        List<Predicate> likePredicates = new LinkedList<>();
        for (String key : searchKeys) {
            likePredicates.add(
                    criteriaBuilder.like(announcementRoot.get("description"), "%" + key + "%")
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
    public LandAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner) {
        return findOne(hasId(id)
                .and(hasUserIdOfOwnerInProperty(userIdOfOwner))
        );
    }

    @Override
    public LandAnnouncement findByIdWithStatus(Long id, AnnouncementStatusEnum status) {
        return findOne(hasId(id)
                .and(hasStatus(status))
        );
    }

}
