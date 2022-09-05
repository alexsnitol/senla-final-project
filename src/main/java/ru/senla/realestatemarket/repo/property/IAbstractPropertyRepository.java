package ru.senla.realestatemarket.repo.property;


import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IAbstractPropertyRepository<M> extends IAbstractRepository<M, Long> {

    M findByIdAndUserIdOfOwner(Long id, Long userIdOfOwner);

    List<M> findAllByUserIdOfOwner(Long userIdOfOwner, String rsqlQuery, Sort sort);

}
