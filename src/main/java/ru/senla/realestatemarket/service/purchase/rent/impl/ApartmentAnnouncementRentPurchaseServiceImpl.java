package ru.senla.realestatemarket.service.purchase.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.purchase.rent.ApartmentAnnouncementRentPurchase;
import ru.senla.realestatemarket.repo.purchase.rent.IApartmentAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.service.purchase.rent.IApartmentAnnouncementRentPurchaseService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ApartmentAnnouncementRentPurchaseServiceImpl
        extends AbstractRentPurchaseServiceImpl<ApartmentAnnouncementRentPurchase>
        implements IApartmentAnnouncementRentPurchaseService {

    private final IApartmentAnnouncementRentPurchaseRepository apartmentAnnouncementRentPurchaseRepository;


    public ApartmentAnnouncementRentPurchaseServiceImpl(
            IApartmentAnnouncementRentPurchaseRepository apartmentAnnouncementRentPurchaseRepository) {
        this.apartmentAnnouncementRentPurchaseRepository = apartmentAnnouncementRentPurchaseRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementRentPurchaseRepository);
        setClazz(ApartmentAnnouncementRentPurchase.class);
    }

}
