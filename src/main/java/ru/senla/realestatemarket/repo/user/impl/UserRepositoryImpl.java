package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IUserRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

import static ru.senla.realestatemarket.repo.user.specification.UserSpecification.hasUsername;

@Slf4j
@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Long> implements IUserRepository {

    @PostConstruct
    public void init() {
        setClazz(User.class);
    }


    @Override
    protected <T> void fetchSelection(From<T, User> from) {
        // fetch did not need it
    }

    @Override
    public User findByUsername(String username) {
        return findOne(hasUsername(username));
    }
}
