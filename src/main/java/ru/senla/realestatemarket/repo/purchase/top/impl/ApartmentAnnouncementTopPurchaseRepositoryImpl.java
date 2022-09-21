package ru.senla.realestatemarket.repo.purchase.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.purchase.top.ApartmentAnnouncementTopPurchase;
import ru.senla.realestatemarket.repo.purchase.top.IApartmentAnnouncementTopPurchaseRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class ApartmentAnnouncementTopPurchaseRepositoryImpl
        extends AbstractTopPurchaseRepositoryImpl<ApartmentAnnouncementTopPurchase>
        implements IApartmentAnnouncementTopPurchaseRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncementTopPurchase.class);
    }

}
