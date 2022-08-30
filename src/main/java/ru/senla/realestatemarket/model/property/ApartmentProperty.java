package ru.senla.realestatemarket.model.property;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.house.ApartmentHouse;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartment_properties")
public class ApartmentProperty extends HousingProperty {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_house_id")
    @JsonManagedReference
    private ApartmentHouse apartmentHouse;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    private Integer floor;


    @PostConstruct
    public void initConstruct() {
        setPropertyType(PropertyTypeEnum.APARTMENT);
    }

}
