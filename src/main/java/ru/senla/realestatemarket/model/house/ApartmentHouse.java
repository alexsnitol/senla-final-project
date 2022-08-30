package ru.senla.realestatemarket.model.house;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment_houses")
public class ApartmentHouse extends House {

    private Boolean elevator;

    @OneToMany(mappedBy = "apartmentHouse", fetch = FetchType.LAZY)
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

}
