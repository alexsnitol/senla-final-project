package ru.senla.realestatemarket.model.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class HousingProperty extends Property {

    @Column(name = "number_of_rooms")
    private Short numberOfRooms;

    @ManyToOne
    @JoinColumn(name = "renovation_type_id")
    private RenovationType renovationType;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
    return true;
}
        if (!(o instanceof HousingProperty)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        HousingProperty that = (HousingProperty) o;
        return Objects.equals(getNumberOfRooms(), that.getNumberOfRooms())
                && Objects.equals(getRenovationType(), that.getRenovationType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumberOfRooms(), getRenovationType());
    }

}
