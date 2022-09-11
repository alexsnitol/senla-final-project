package ru.senla.realestatemarket.mapper.dictionaty;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.dictionary.UpdateRequestAnnouncementTopPriceDto;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;

@Mapper
public abstract class AnnouncementTopPriceMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateAnnouncementTopPriceFromUpdateRequestAnnouncementTopPriceDto(
            UpdateRequestAnnouncementTopPriceDto updateRequestAnnouncementTopPriceDto,
            @MappingTarget AnnouncementTopPrice announcementTopPrice
    );

}
