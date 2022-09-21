package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestApartmentPropertyWithUserIdOfOwnerDto extends RequestApartmentPropertyDto {

    @NotNull
    private Long userIdOfOwner;

}
