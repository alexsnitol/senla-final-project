package ru.senla.realestatemarket.repo.purchase.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.purchase.rent.ApartmentAnnouncementRentPurchase;
import ru.senla.realestatemarket.repo.purchase.rent.IApartmentAnnouncementRentPurchaseRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class ApartmentAnnouncementRentPurchaseRepositoryImpl
        extends AbstractRentPurchaseRepositoryImpl<ApartmentAnnouncementRentPurchase>
        implements IApartmentAnnouncementRentPurchaseRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncementRentPurchase.class);
    }

}
