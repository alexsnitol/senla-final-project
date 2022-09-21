package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.property.IAbstractPropertyRepository;

import java.util.List;

import static ru.senla.realestatemarket.repo.property.specification.GenericPropertySpecification.hasIdAndUserIdOfOwner;
import static ru.senla.realestatemarket.repo.property.specification.GenericPropertySpecification.hasUserIdOfOwner;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public abstract class AbstractPropertyRepositoryImpl<M> extends AbstractRepositoryImpl<M, Long>
        implements IAbstractPropertyRepository<M> {

    @Override
    public M findByIdAndUserIdOfOwner(Long id, Long userIdOfOwner) {
        return findOne(
                hasIdAndUserIdOfOwner(id, userIdOfOwner)
        );
    }

    @Override
    public List<M> findAllByUserIdOfOwner(Long userIdOfOwner, String rsqlQuery, Sort sort) {
        return findAllByQuery(
                hasUserIdOfOwner(userIdOfOwner),
                rsqlQuery, sort
        );
    }

}
