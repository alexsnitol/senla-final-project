package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IRoleRepository;

import javax.annotation.PostConstruct;

import static ru.senla.realestatemarket.repo.user.specification.RoleSpecification.hasName;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role, Long> implements IRoleRepository {

    @PostConstruct
    public void init() {
        setClazz(Role.class);
    }


    @Override
    public Role findByName(String name) {
        return findOne(
                hasName(name)
        );
    }
}
