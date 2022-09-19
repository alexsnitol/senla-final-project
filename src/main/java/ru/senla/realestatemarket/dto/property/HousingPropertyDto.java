package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class HousingPropertyDto extends PropertyDto {

    private Short numberOfRooms;

    private String renovationType;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingPropertyDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        HousingPropertyDto that = (HousingPropertyDto) o;
        return Objects.equals(getNumberOfRooms(), that.getNumberOfRooms())
                && Objects.equals(getRenovationType(), that.getRenovationType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumberOfRooms(), getRenovationType());
    }

}
