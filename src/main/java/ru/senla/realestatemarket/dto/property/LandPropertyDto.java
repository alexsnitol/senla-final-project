package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.address.AddressDto;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class LandPropertyDto extends PropertyDto {

    private AddressDto address;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandPropertyDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LandPropertyDto that = (LandPropertyDto) o;
        return Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddress());
    }

}
