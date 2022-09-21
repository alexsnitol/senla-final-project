package ru.senla.realestatemarket.model.house;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment_houses")
public class ApartmentHouse extends House {

    private Boolean elevator;

    @OneToMany(mappedBy = "apartmentHouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ApartmentProperty> propertyList;

    @Transient
    private Integer numberOfApartmentProperties;


    @PostConstruct
    public void initConstruct() {
        setHouseType(HouseTypeEnum.APARTMENT_HOUSE);
    }

    @PostLoad
    public void initLoad() {
        numberOfApartmentProperties = propertyList.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentHouse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentHouse that = (ApartmentHouse) o;
        return Objects.equals(getElevator(), that.getElevator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElevator());
    }
}
