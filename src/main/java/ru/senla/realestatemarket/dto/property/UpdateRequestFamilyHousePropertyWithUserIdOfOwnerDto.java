package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto extends UpdateRequestFamilyHousePropertyDto {

    private Long userIdOfOwner;

}
