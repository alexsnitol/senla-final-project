package ru.senla.realestatemarket.service.dictionary;

import ru.senla.realestatemarket.dto.dictionary.UpdateRequestAnnouncementTopPriceDto;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.service.IAbstractService;

public interface IAnnouncementTopPriceService extends IAbstractService<AnnouncementTopPrice, Long> {

    void updateById(UpdateRequestAnnouncementTopPriceDto updateRequestAnnouncementTopPriceDto, Long id);

}
