package ru.senla.realestatemarket.dto.dictionary;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class UpdateRequestAnnouncementTopPriceDto {

    @Min(value = 0)
    private Float pricePerHour;

}
