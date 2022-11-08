package ru.senla.realestatemarket.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.user.Authority;
import ru.senla.realestatemarket.repo.user.IAuthorityRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.user.IAuthorityService;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class AuthorityServiceImpl extends AbstractServiceImpl<Authority, Long> implements IAuthorityService {

    private final IAuthorityRepository authorityRepository;


    public AuthorityServiceImpl(IAuthorityRepository authorityRepository) {
        this.clazz = Authority.class;
        this.defaultRepository = authorityRepository;

        this.authorityRepository = authorityRepository;
    }

}
