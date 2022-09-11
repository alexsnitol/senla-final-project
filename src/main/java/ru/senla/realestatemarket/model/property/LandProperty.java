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
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "land_properties")
public class LandProperty extends Property implements IPropertyWithAnnouncementList<LandAnnouncement> {

    @ManyToOne
    @JsonIgnore
    @Setter
    @JoinColumn(name = "street_id")
    private Street street;

    @Transient
    @Getter
    private Address address;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LandAnnouncement> announcementList;


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
