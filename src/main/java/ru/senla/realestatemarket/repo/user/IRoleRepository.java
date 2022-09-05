package ru.senla.realestatemarket.repo.user;

import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.repo.IAbstractRepository;

public interface IRoleRepository extends IAbstractRepository<Role, Long> {

    Role findByName(String name);

}
