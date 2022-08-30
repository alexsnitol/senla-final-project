package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.mapper.property.FamilyHousePropertyMapper;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {FamilyHousePropertyMapper.class, AnnouncementStatusEnumMapper.class, AnnouncementTypeEnumMapper.class})
public abstract class FamilyHouseAnnouncementMapper {

    public abstract FamilyHouseAnnouncementDto toFamilyHouseAnnouncementDto(
            FamilyHouseAnnouncement familyHouseAnnouncement);

    public abstract List<FamilyHouseAnnouncementDto> toFamilyHouseAnnouncementDto(
            Collection<FamilyHouseAnnouncement> familyHouseAnnouncements);

    public abstract FamilyHouseAnnouncement toFamilyHouseAnnouncement(
            RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto);

}
