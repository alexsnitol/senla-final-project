package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;

@Mapper
public abstract class AnnouncementTypeEnumMapper {

    public NonHousingAnnouncementTypeEnum toNonHousingAnnouncementTypeEnum(AnnouncementTypeEnum announcementTypeEnum) {
        switch (announcementTypeEnum) {
            case SELL:
                return NonHousingAnnouncementTypeEnum.valueOf(announcementTypeEnum.name());
            default:
                return null;
        }
    }

    public HousingAnnouncementTypeEnum toHousingAnnouncementTypeEnum(AnnouncementTypeEnum announcementTypeEnum) {
        switch (announcementTypeEnum) {
            case SELL:
            case DAILY_RENT:
            case MONTHLY_RENT:
                return HousingAnnouncementTypeEnum.valueOf(announcementTypeEnum.name());
            default:
                return null;
        }
    }

    public AnnouncementTypeEnum nonHousingAnnouncementTypeEnumToAnnouncementTypeEnum(
            NonHousingAnnouncementTypeEnum nonHousingAnnouncementTypeEnum) {
        return AnnouncementTypeEnum.valueOf(nonHousingAnnouncementTypeEnum.name());
    }

    public AnnouncementTypeEnum housingAnnouncementTypeEnumToAnnouncementTypeEnum(
            HousingAnnouncementTypeEnum housingAnnouncementTypeEnum) {
        return AnnouncementTypeEnum.valueOf(housingAnnouncementTypeEnum.name());
    }

}
