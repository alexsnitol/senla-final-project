package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestFullAddressWithoutHouseNumberDto {

    @NotNull
    private Long regionId;

    @NotNull
    private Long cityId;

    @NotNull
    private Long streetId;

}
