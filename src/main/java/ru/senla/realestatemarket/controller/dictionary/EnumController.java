package ru.senla.realestatemarket.controller.dictionary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.RentAnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.SellAnnouncementStatusEnum;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/housing-types")
    public HousingTypeEnum[] getAllHousingType() {
        return HousingTypeEnum.values();
    }

    @GetMapping("/property-types")
    public PropertyTypeEnum[] getAllPropertyType() {
        return PropertyTypeEnum.values();
    }

    @GetMapping("/property-statuses")
    public PropertyStatusEnum[] getAllPropertyStatuses() {
        return PropertyStatusEnum.values();
    }

    @GetMapping("/sell-announcement-statuses")
    public SellAnnouncementStatusEnum[] getAllSellAnnouncementStatuses() {
        return SellAnnouncementStatusEnum.values();
    }

    @GetMapping("/rent-announcement-statuses")
    public RentAnnouncementStatusEnum[] getAllRentAnnouncementStatuses() {
        return RentAnnouncementStatusEnum.values();
    }

    @GetMapping("/housing-announcement-types")
    public HousingAnnouncementTypeEnum[] getAllHousingAnnouncementTypes() {
        return HousingAnnouncementTypeEnum.values();
    }

    @GetMapping("/non-housing-announcement-types")
    public NonHousingAnnouncementTypeEnum[] getAllNonHousingAnnouncementTypes() {
        return NonHousingAnnouncementTypeEnum.values();
    }

}
