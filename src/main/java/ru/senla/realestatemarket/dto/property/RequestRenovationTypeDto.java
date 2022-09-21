package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestRenovationTypeDto {

    @NotBlank
    public String name;

}
