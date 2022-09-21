package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IUserRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import static ru.senla.realestatemarket.repo.user.specification.UserSpecification.hasUsername;
import static ru.senla.realestatemarket.repo.user.specification.UserSpecification.notHasId;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Long> implements IUserRepository {

    @PostConstruct
    public void init() {
        setClazz(User.class);
    }


    @Override
    public User findByUsername(String username) {
        return findOne(hasUsername(username));
    }

    @Override
    public User findByUsernameExcludingId(String username, Long excludingUserId) {
        return findOne(
                hasUsername(username)
                .and(notHasId(excludingUserId))
        );
    }

    @Override
    public User findByIdWithFetchingRolesAndAuthorities(Long id) {
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        root.fetch("roles", JoinType.LEFT);
        //root.fetch("roles", JoinType.LEFT).fetch("authorities", JoinType.LEFT);
//        root.join("roles").join("authorities");

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("id"), id));

        return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
    }

}
