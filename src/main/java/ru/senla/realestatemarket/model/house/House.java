package ru.senla.realestatemarket.model.house;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.address.Address;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class House implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_houses", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_houses")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "number_of_floors")
    private Short numberOfFloors;

    @Column(name = "building_year")
    private Short buildingYear;

    @ManyToOne
    @JoinColumn(name = "house_material_id")
    private HouseMaterial houseMaterial;

    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type")
    private HousingTypeEnum housingType;

    @Transient
    @Setter(AccessLevel.PROTECTED)
    private HouseTypeEnum houseType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return Objects.equals(getId(), house.getId())
                && Objects.equals(getAddress(), house.getAddress())
                && Objects.equals(getNumberOfFloors(), house.getNumberOfFloors())
                && Objects.equals(getBuildingYear(), house.getBuildingYear())
                && Objects.equals(getHouseMaterial(), house.getHouseMaterial())
                && getHousingType() == house.getHousingType()
                && getHouseType() == house.getHouseType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getNumberOfFloors(), getBuildingYear(),
                getHouseMaterial(), getHousingType(), getHouseType());
    }

}
