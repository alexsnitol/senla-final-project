package ru.senla.realestatemarket.repo.purchase.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;
import ru.senla.realestatemarket.repo.purchase.top.IFamilyHouseAnnouncementTopPurchaseRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class FamilyHouseAnnouncementTopPurchaseRepositoryImpl
        extends AbstractTopPurchaseRepositoryImpl<FamilyHouseAnnouncementTopPurchase>
        implements IFamilyHouseAnnouncementTopPurchaseRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncementTopPurchase.class);
    }

}
