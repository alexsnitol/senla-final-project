package ru.senla.realestatemarket.mapper.announcement;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestLandAnnouncementDto;
import ru.senla.realestatemarket.mapper.property.LandPropertyMapper;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {LandPropertyMapper.class, AnnouncementStatusEnumMapper.class, AnnouncementTypeEnumMapper.class})
public abstract class LandAnnouncementMapper {

    public abstract LandAnnouncementDto toLandAnnouncementDto(LandAnnouncement landAnnouncement);

    public abstract List<LandAnnouncementDto> toLandAnnouncementDto(Collection<LandAnnouncement> landAnnouncements);

    public abstract LandAnnouncement toLandAnnouncement(RequestLandAnnouncementDto requestLandAnnouncementDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateLandAnnouncementFromUpdateRequestLandAnnouncement(
            UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto,
            @MappingTarget LandAnnouncement landAnnouncement
    );

}
