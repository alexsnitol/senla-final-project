package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class UpdateRequestAddressDto {

    private Long streetId;

    @Size(min = 1, max = 255)
    private String houseNumber;

}
