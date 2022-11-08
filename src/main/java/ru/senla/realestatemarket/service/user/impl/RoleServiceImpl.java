package ru.senla.realestatemarket.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.repo.user.IRoleRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.user.IRoleService;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, Long> implements IRoleService {

    private final IRoleRepository roleRepository;


    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.clazz = Role.class;
        this.defaultRepository = roleRepository;

        this.roleRepository = roleRepository;
    }

}
