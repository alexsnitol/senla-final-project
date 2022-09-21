package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestHouseNumberDto {

    @NotBlank
    @Size(max = 255)
    private String houseNumber;

}
