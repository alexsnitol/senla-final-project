package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.senla.realestatemarket.dto.announcement.AnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.HousingAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class AnnouncementMapper {

    protected ApartmentAnnouncementMapper apartmentAnnouncementMapper;
    protected FamilyHouseAnnouncementMapper familyHouseAnnouncementMapper;
    protected HousingAnnouncementMapper housingAnnouncementMapper;
    protected LandAnnouncementMapper landAnnouncementMapper;


    @Autowired
    public void setApartmentAnnouncementMapper(ApartmentAnnouncementMapper apartmentAnnouncementMapper) {
        this.apartmentAnnouncementMapper = apartmentAnnouncementMapper;
    }

    @Autowired
    public void setFamilyHouseAnnouncementMapper(FamilyHouseAnnouncementMapper familyHouseAnnouncementMapper) {
        this.familyHouseAnnouncementMapper = familyHouseAnnouncementMapper;
    }

    @Autowired
    public void setHousingAnnouncementMapper(HousingAnnouncementMapper housingAnnouncementMapper) {
        this.housingAnnouncementMapper = housingAnnouncementMapper;
    }

    @Autowired
    public void setLandAnnouncementMapper(LandAnnouncementMapper landAnnouncementMapper) {
        this.landAnnouncementMapper = landAnnouncementMapper;
    }


    public abstract AnnouncementDto toAnnouncementDto(Announcement announcement);

    public abstract List<AnnouncementDto> toAnnouncementDto(Collection<Announcement> announcements);

    @Named("MappedInheritors")
    public AnnouncementDto toAnnouncementDtoWithMappedInheritors(Announcement announcement) {
        if (announcement instanceof ApartmentAnnouncement) {
            ApartmentAnnouncementDto apartmentAnnouncementDto
                    = apartmentAnnouncementMapper.toApartmentAnnouncementDto((ApartmentAnnouncement) announcement);

            apartmentAnnouncementDto.setPropertyType(PropertyTypeEnum.APARTMENT);

            return apartmentAnnouncementDto;
        } else if (announcement instanceof FamilyHouseAnnouncement) {
            FamilyHouseAnnouncementDto familyHouseAnnouncementDto
                    = familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(
                            (FamilyHouseAnnouncement) announcement);

            familyHouseAnnouncementDto.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);

            return familyHouseAnnouncementDto;
        } else if (announcement instanceof HousingAnnouncement) {
            HousingAnnouncementDto housingAnnouncementDto
                    = housingAnnouncementMapper.toHousingAnnouncementDto((HousingAnnouncement) announcement);

            return housingAnnouncementDto;
        } else if (announcement instanceof LandAnnouncement) {
            LandAnnouncementDto landAnnouncementDto
                    = landAnnouncementMapper.toLandAnnouncementDto((LandAnnouncement) announcement);

            landAnnouncementDto.setPropertyType(PropertyTypeEnum.LAND);

            return landAnnouncementDto;
        } else {
            return toAnnouncementDto(announcement);
        }
    }

    @IterableMapping(qualifiedByName = "MappedInheritors")
    public abstract List<AnnouncementDto> toAnnouncementDtoWithMappedInheritors(Collection<Announcement> announcements);

}
