package ru.senla.realestatemarket.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.user.Authority;
import ru.senla.realestatemarket.repo.user.IAuthorityRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.user.IAuthorityService;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl extends AbstractServiceImpl<Authority, Long> implements IAuthorityService {

    private final IAuthorityRepository authorityRepository;


    @PostConstruct
    public void init() {
        setDefaultRepository(authorityRepository);
        setClazz(Authority.class);
    }

}
