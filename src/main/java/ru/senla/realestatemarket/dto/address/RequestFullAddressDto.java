package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestFullAddressDto {

    @NotNull
    private Long regionId;

    @NotNull
    private Long cityId;

    @NotNull
    private Long streetId;

    @NotBlank
    @Size(max = 255)
    private String houseNumber;

}
