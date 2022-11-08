package ru.senla.realestatemarket.service.dictionary.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.dictionary.UpdateRequestAnnouncementTopPriceDto;
import ru.senla.realestatemarket.mapper.dictionaty.AnnouncementTopPriceMapper;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.dictionary.IAnnouncementTopPriceService;

import javax.transaction.Transactional;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class AnnouncementTopPriceServiceImpl
        extends AbstractServiceImpl<AnnouncementTopPrice, Long>
        implements IAnnouncementTopPriceService {

    private final IAnnouncementTopPriceRepository announcementTopPriceRepository;

    private final AnnouncementTopPriceMapper announcementTopPriceMapper;


    public AnnouncementTopPriceServiceImpl(
            IAnnouncementTopPriceRepository announcementTopPriceRepository,
            AnnouncementTopPriceMapper announcementTopPriceMapper
    ) {
        this.clazz = AnnouncementTopPrice.class;
        this.defaultRepository = announcementTopPriceRepository;

        this.announcementTopPriceRepository = announcementTopPriceRepository;
        this.announcementTopPriceMapper = announcementTopPriceMapper;
    }


    @Override
    @Transactional
    public void updateById(UpdateRequestAnnouncementTopPriceDto updateRequestAnnouncementTopPriceDto, Long id) {
        AnnouncementTopPrice announcementTopPrice = getById(id);

        announcementTopPriceMapper.updateAnnouncementTopPriceFromUpdateRequestAnnouncementTopPriceDto(
                updateRequestAnnouncementTopPriceDto,
                announcementTopPrice
        );

        announcementTopPriceRepository.update(announcementTopPrice);
    }

}
