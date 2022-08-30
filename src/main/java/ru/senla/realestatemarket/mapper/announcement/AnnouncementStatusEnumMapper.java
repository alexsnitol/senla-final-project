package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.RentAnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.SellAnnouncementStatusEnum;

@Mapper
public abstract class AnnouncementStatusEnumMapper {

    public SellAnnouncementStatusEnum toSellAnnouncementStatusEnum(AnnouncementStatusEnum announcementStatusEnum) {
        switch (announcementStatusEnum) {
            case OPEN:
            case CLOSED:
            case DELETED:
                return SellAnnouncementStatusEnum.valueOf(announcementStatusEnum.name());
            default:
                return null;
        }
    }

    public RentAnnouncementStatusEnum toRentAnnouncementStatusEnum(AnnouncementStatusEnum announcementStatusEnum) {
        switch (announcementStatusEnum) {
            case OPEN:
            case DELETED:
                return RentAnnouncementStatusEnum.valueOf(announcementStatusEnum.name());
            default:
                return null;
        }
    }

    public AnnouncementStatusEnum sellAnnouncementStatusEnumToAnnouncementStatusEnum(
            SellAnnouncementStatusEnum sellAnnouncementStatusEnum) {
        return AnnouncementStatusEnum.valueOf(sellAnnouncementStatusEnum.name());
    }

    public AnnouncementStatusEnum rentAnnouncementStatusEnumToAnnouncementStatusEnum(
            RentAnnouncementStatusEnum rentAnnouncementStatusEnum) {
        return AnnouncementStatusEnum.valueOf(rentAnnouncementStatusEnum.name());
    }

}
