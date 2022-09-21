package ru.senla.realestatemarket.dto.house;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestHouseMaterialDto {

    @NotBlank
    private String name;

}
