package ru.senla.realestatemarket.repo.purchase.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.purchase.rent.FamilyHouseAnnouncementRentPurchase;
import ru.senla.realestatemarket.repo.purchase.rent.IFamilyHouseAnnouncementRentPurchaseRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class FamilyHouseAnnouncementRentPurchaseRepositoryImpl
        extends AbstractRentPurchaseRepositoryImpl<FamilyHouseAnnouncementRentPurchase>
        implements IFamilyHouseAnnouncementRentPurchaseRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncementRentPurchase.class);
    }

}
