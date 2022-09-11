package ru.senla.realestatemarket.repo.dictionary.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;

import javax.annotation.PostConstruct;

import static ru.senla.realestatemarket.repo.dictionary.specification.AnnouncementTopPriceSpecification.hasAnnouncementType;
import static ru.senla.realestatemarket.repo.dictionary.specification.AnnouncementTopPriceSpecification.hasPropertyType;

@Slf4j
@Repository
public class AnnouncementTopPriceRepositoryImpl
        extends AbstractRepositoryImpl<AnnouncementTopPrice, Long>
        implements IAnnouncementTopPriceRepository {

    @PostConstruct
    public void init() {
        setClazz(AnnouncementTopPrice.class);
    }


    @Override
    public AnnouncementTopPrice findPriceForApartmentSellAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.APARTMENT)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.SELL))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceForApartmentDailyRentAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.APARTMENT)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.DAILY_RENT))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceForApartmentMonthlyRentAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.APARTMENT)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.MONTHLY_RENT))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceForFamilyHouseSellAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.FAMILY_HOUSE)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.SELL))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceForFamilyHouseDailyRentAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.FAMILY_HOUSE)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.DAILY_RENT))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceForFamilyHouseMonthlyRentAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.FAMILY_HOUSE)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.MONTHLY_RENT))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceForLandSellAnnouncement() {
        return findOne(
                hasPropertyType(PropertyTypeEnum.LAND)
                        .and(hasAnnouncementType(AnnouncementTypeEnum.SELL))
        );
    }

    @Override
    public AnnouncementTopPrice findPriceByPropertyTypeAndAnnouncementType(
            PropertyTypeEnum propertyType, AnnouncementTypeEnum announcementType
    ) {
        return findOne(
                hasPropertyType(propertyType)
                .and(hasAnnouncementType(announcementType))
        );
    }

}
