package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.HousingAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class HousingAnnouncementMapper {

    private final ApartmentAnnouncementMapper apartmentAnnouncementMapper
            = Mappers.getMapper(ApartmentAnnouncementMapper.class);

    private final FamilyHouseAnnouncementMapper familyHouseAnnouncementMapper
            = Mappers.getMapper(FamilyHouseAnnouncementMapper.class);


    public abstract HousingAnnouncementDto toHousingAnnouncementDto(HousingAnnouncement housingAnnouncement);

    public abstract List<HousingAnnouncementDto> toHousingAnnouncementDto(
            Collection<HousingAnnouncement> housingAnnouncements);

    @Named("MappedInheritors")
    public HousingAnnouncementDto toHousingAnnouncementDtoWithMappedInheritors(HousingAnnouncement housingAnnouncement) {
        if (housingAnnouncement instanceof ApartmentAnnouncement) {
            ApartmentAnnouncementDto apartmentAnnouncementDto
                    = apartmentAnnouncementMapper.toApartmentAnnouncementDto((ApartmentAnnouncement) housingAnnouncement);

            apartmentAnnouncementDto.setPropertyType(PropertyTypeEnum.APARTMENT);

            return apartmentAnnouncementDto;
        } else if (housingAnnouncement instanceof FamilyHouseAnnouncement) {
            FamilyHouseAnnouncementDto familyHouseAnnouncementDto
                    = familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto((FamilyHouseAnnouncement) housingAnnouncement);

            familyHouseAnnouncementDto.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);

            return familyHouseAnnouncementDto;
        } else {
            return toHousingAnnouncementDto(housingAnnouncement);
        }
    }

    @IterableMapping(qualifiedByName = "MappedInheritors")
    public abstract List<HousingAnnouncementDto> toHousingAnnouncementDtoWithMappedInheritors(Collection<HousingAnnouncement> housingAnnouncements);


}
