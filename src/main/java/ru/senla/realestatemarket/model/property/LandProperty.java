package ru.senla.realestatemarket.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "land_properties")
public class LandProperty extends Property {

    @ManyToOne
    @JsonIgnore
    @Setter
    @JoinColumn(name = "street_id")
    private Street street;

    @Transient
    @Getter
    private Address address;


    @PostConstruct
    public void initConstruct() {
        setPropertyType(PropertyTypeEnum.LAND);
    }

    @PostLoad
    public void initLoad() {
        City city = street.getCity();
        Region region = city.getRegion();

        address = new Address();
        address.setRegion(region);
        address.setCity(city);
        address.setStreet(street);
    }

}
