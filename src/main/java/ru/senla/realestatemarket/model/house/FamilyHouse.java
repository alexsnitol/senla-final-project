package ru.senla.realestatemarket.model.house;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family_houses")
public class FamilyHouse extends House {

    @Column(name = "swimming_pool")
    private Boolean swimmingPool;

    @OneToOne(mappedBy = "familyHouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private FamilyHouseProperty property;


    @PostConstruct
    public void init() {
        setHouseType(HouseTypeEnum.FAMILY_HOUSE);
    }

}
