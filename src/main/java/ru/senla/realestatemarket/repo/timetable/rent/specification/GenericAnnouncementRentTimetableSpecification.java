package ru.senla.realestatemarket.repo.timetable.rent.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;

public class GenericAnnouncementRentTimetableSpecification {

    private GenericAnnouncementRentTimetableSpecification() {}


    public static <T> Specification<T> hasUserIdOfTenant(Long userIdOfTenant) {
        return (root, query, criteriaBuilder) -> {
            Join<T, User> userJoin = root.join("tenant");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfTenant);
        };
    }

}
