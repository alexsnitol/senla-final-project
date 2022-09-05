package ru.senla.realestatemarket.service.purchase.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.purchase.top.LandAnnouncementTopPurchase;
import ru.senla.realestatemarket.repo.purchase.top.ILandAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.service.purchase.top.ILandAnnouncementTopPurchaseService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class LandAnnouncementTopPurchaseServiceImpl
        extends AbstractTopPurchaseServiceImpl<LandAnnouncementTopPurchase>
        implements ILandAnnouncementTopPurchaseService {

    private final ILandAnnouncementTopPurchaseRepository landAnnouncementTopPurchaseRepository;


    public LandAnnouncementTopPurchaseServiceImpl(
            ILandAnnouncementTopPurchaseRepository landAnnouncementTopPurchaseRepository) {
        this.landAnnouncementTopPurchaseRepository = landAnnouncementTopPurchaseRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(landAnnouncementTopPurchaseRepository);
        setClazz(LandAnnouncementTopPurchase.class);
    }

}
