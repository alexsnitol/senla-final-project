package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.Authority;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IAuthorityRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class AuthorityRepositoryImpl extends AbstractRepositoryImpl<Authority, Long> implements IAuthorityRepository {

    @PostConstruct
    public void init() {
        setClazz(Authority.class);
    }


}
