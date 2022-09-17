package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.mapper.property.FamilyHousePropertyMapper;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {FamilyHousePropertyMapper.class, AnnouncementStatusEnumMapper.class, AnnouncementTypeEnumMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class FamilyHouseAnnouncementMapper {

    public abstract FamilyHouseAnnouncementDto toFamilyHouseAnnouncementDto(
            FamilyHouseAnnouncement familyHouseAnnouncement);

    public abstract List<FamilyHouseAnnouncementDto> toFamilyHouseAnnouncementDto(
            Collection<FamilyHouseAnnouncement> familyHouseAnnouncements);

    public abstract FamilyHouseAnnouncement toFamilyHouseAnnouncement(
            RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFamilyHouseAnnouncementFormUpdateRequestFamilyHouseAnnouncement(
            UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto,
            @MappingTarget FamilyHouseAnnouncement familyHouseAnnouncement
    );

}
