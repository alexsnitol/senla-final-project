package ru.senla.realestatemarket.service.purchase.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;
import ru.senla.realestatemarket.repo.purchase.top.IFamilyHouseAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.service.purchase.top.IFamilyHouseAnnouncementTopPurchaseService;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class FamilyHouseAnnouncementTopPurchaseServiceImpl
        extends AbstractTopPurchaseServiceImpl<FamilyHouseAnnouncementTopPurchase>
        implements IFamilyHouseAnnouncementTopPurchaseService {

    private final IFamilyHouseAnnouncementTopPurchaseRepository familyHouseAnnouncementTopPurchaseRepository;


    public FamilyHouseAnnouncementTopPurchaseServiceImpl(
            IFamilyHouseAnnouncementTopPurchaseRepository familyHouseAnnouncementTopPurchaseRepository) {
        this.familyHouseAnnouncementTopPurchaseRepository = familyHouseAnnouncementTopPurchaseRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseAnnouncementTopPurchaseRepository);
        setClazz(FamilyHouseAnnouncementTopPurchase.class);
    }

}
