package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.address.AddressDto;

@Getter
@Setter
public class LandPropertyDto extends PropertyDto {

    private AddressDto address;

}
