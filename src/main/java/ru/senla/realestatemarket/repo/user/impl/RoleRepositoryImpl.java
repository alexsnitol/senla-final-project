package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IRoleRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

@Slf4j
@Repository
public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role, Long> implements IRoleRepository {

    @PostConstruct
    public void init() {
        setClazz(Role.class);
    }


    @Override
    protected <T> void fetchSelection(From<T, Role> from) {
        // fetch did not need it
    }

}
