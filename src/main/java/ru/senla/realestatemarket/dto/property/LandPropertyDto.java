package ru.senla.realestatemarket.dto.property;

import lombok.Data;
import ru.senla.realestatemarket.dto.address.AddressDto;

@Data
public class LandPropertyDto extends PropertyDto {

    private AddressDto address;

}
