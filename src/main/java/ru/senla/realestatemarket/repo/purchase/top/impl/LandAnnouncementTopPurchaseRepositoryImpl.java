package ru.senla.realestatemarket.repo.purchase.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.purchase.top.LandAnnouncementTopPurchase;
import ru.senla.realestatemarket.repo.purchase.top.ILandAnnouncementTopPurchaseRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class LandAnnouncementTopPurchaseRepositoryImpl
        extends AbstractTopPurchaseRepositoryImpl<LandAnnouncementTopPurchase>
        implements ILandAnnouncementTopPurchaseRepository {

    @PostConstruct
    public void init() {
        setClazz(LandAnnouncementTopPurchase.class);
    }

}
