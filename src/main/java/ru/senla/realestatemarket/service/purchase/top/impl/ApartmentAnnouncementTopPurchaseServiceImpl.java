package ru.senla.realestatemarket.service.purchase.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.purchase.top.ApartmentAnnouncementTopPurchase;
import ru.senla.realestatemarket.repo.purchase.top.IApartmentAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.service.purchase.top.IApartmentAnnouncementTopPurchaseService;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class ApartmentAnnouncementTopPurchaseServiceImpl
        extends AbstractTopPurchaseServiceImpl<ApartmentAnnouncementTopPurchase>
        implements IApartmentAnnouncementTopPurchaseService {

    private final IApartmentAnnouncementTopPurchaseRepository apartmentAnnouncementTopPurchaseRepository;


    public ApartmentAnnouncementTopPurchaseServiceImpl(
            IApartmentAnnouncementTopPurchaseRepository apartmentAnnouncementTopPurchaseRepository) {
        this.apartmentAnnouncementTopPurchaseRepository = apartmentAnnouncementTopPurchaseRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementTopPurchaseRepository);
        setClazz(ApartmentAnnouncementTopPurchase.class);
    }

}
