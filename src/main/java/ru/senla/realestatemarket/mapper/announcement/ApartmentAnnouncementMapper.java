package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.mapper.property.ApartmentPropertyMapper;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {ApartmentPropertyMapper.class, AnnouncementStatusEnumMapper.class, AnnouncementTypeEnumMapper.class})
public abstract class ApartmentAnnouncementMapper {

    public abstract ApartmentAnnouncementDto toApartmentAnnouncementDto(ApartmentAnnouncement apartmentAnnouncement);

    public abstract List<ApartmentAnnouncementDto> toApartmentAnnouncementDto(
            Collection<ApartmentAnnouncement> apartmentAnnouncements);

    public abstract ApartmentAnnouncement toApartmentAnnouncement(
            RequestApartmentAnnouncementDto requestApartmentAnnouncementDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateApartmentAnnouncementFromUpdateRequestApartmentAnnouncementDto(
            UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto,
            @MappingTarget ApartmentAnnouncement apartmentAnnouncement
    );

}
