package ru.senla.realestatemarket.mapper.dictionaty;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.dictionary.UpdateRequestAnnouncementTopPriceDto;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class AnnouncementTopPriceMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateAnnouncementTopPriceFromUpdateRequestAnnouncementTopPriceDto(
            UpdateRequestAnnouncementTopPriceDto updateRequestAnnouncementTopPriceDto,
            @MappingTarget AnnouncementTopPrice announcementTopPrice
    );

}
