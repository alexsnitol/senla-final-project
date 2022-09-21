package ru.senla.realestatemarket.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.repo.user.IRoleRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.user.IRoleService;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, Long> implements IRoleService {

    private final IRoleRepository roleRepository;


    @PostConstruct
    public void init() {
        setDefaultRepository(roleRepository);
        setClazz(Role.class);
    }

}
