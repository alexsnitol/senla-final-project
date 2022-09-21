package ru.senla.realestatemarket.service.purchase.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.purchase.rent.FamilyHouseAnnouncementRentPurchase;
import ru.senla.realestatemarket.repo.purchase.rent.IFamilyHouseAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.service.purchase.rent.IFamilyHouseAnnouncementRentPurchaseService;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class FamilyHouseAnnouncementRentPurchaseServiceImpl
        extends AbstractRentPurchaseServiceImpl<FamilyHouseAnnouncementRentPurchase>
        implements IFamilyHouseAnnouncementRentPurchaseService {

    private final IFamilyHouseAnnouncementRentPurchaseRepository familyHouseAnnouncementRentPurchaseRepository;


    public FamilyHouseAnnouncementRentPurchaseServiceImpl(
            IFamilyHouseAnnouncementRentPurchaseRepository familyHouseAnnouncementRentPurchaseRepository) {
        this.familyHouseAnnouncementRentPurchaseRepository = familyHouseAnnouncementRentPurchaseRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseAnnouncementRentPurchaseRepository);
        setClazz(FamilyHouseAnnouncementRentPurchase.class);
    }

}
