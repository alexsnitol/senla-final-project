package ru.senla.realestatemarket.repo.user;

import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.IAbstractRepository;

public interface IUserRepository extends IAbstractRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameExcludingId(String username, Long excludingUserId);

    User findByIdWithFetchingRolesAndAuthorities(Long id);
}
