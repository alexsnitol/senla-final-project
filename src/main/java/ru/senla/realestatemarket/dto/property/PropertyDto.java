package ru.senla.realestatemarket.dto.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.user.SimplyUserWithContactsAndRatingDto;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PropertyDto {

    private Long id;

    private Float area;

    private SimplyUserWithContactsAndRatingDto owner;

    private PropertyStatusEnum status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PropertyTypeEnum propertyType;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertyDto)) {
            return false;
        }
        PropertyDto that = (PropertyDto) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getArea(), that.getArea())
                && Objects.equals(getOwner(), that.getOwner())
                && getStatus() == that.getStatus() && getPropertyType() == that.getPropertyType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArea(), getOwner(), getStatus(), getPropertyType());
    }

}
