package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestApartmentPropertyWithUserIdOfOwnerDto extends UpdateRequestApartmentPropertyDto {

    private Long userIdOfOwner;

}
