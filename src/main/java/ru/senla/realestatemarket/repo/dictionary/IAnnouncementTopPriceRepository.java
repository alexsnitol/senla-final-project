package ru.senla.realestatemarket.repo.dictionary;

import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.repo.IAbstractRepository;

public interface IAnnouncementTopPriceRepository extends IAbstractRepository<AnnouncementTopPrice, Long> {
    AnnouncementTopPrice findPriceForApartmentSellAnnouncement();

    AnnouncementTopPrice findPriceForApartmentDailyRentAnnouncement();

    AnnouncementTopPrice findPriceForApartmentMonthlyRentAnnouncement();

    AnnouncementTopPrice findPriceForFamilyHouseSellAnnouncement();

    AnnouncementTopPrice findPriceForFamilyHouseDailyRentAnnouncement();

    AnnouncementTopPrice findPriceForFamilyHouseMonthlyRentAnnouncement();

    AnnouncementTopPrice findPriceForLandSellAnnouncement();

    AnnouncementTopPrice findPriceByPropertyTypeAndAnnouncementType(
            PropertyTypeEnum propertyType, AnnouncementTypeEnum announcementType
    );
}
