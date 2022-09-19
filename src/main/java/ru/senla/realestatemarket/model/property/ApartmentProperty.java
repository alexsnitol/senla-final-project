package ru.senla.realestatemarket.model.property;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.house.ApartmentHouse;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartment_properties")
public class ApartmentProperty extends HousingProperty implements IPropertyWithAnnouncementList<ApartmentAnnouncement> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_house_id")
    @JsonManagedReference
    private ApartmentHouse apartmentHouse;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    private Short floor;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ApartmentAnnouncement> announcementList;


    @PostConstruct
    public void initConstruct() {
        setPropertyType(PropertyTypeEnum.APARTMENT);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentProperty)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentProperty that = (ApartmentProperty) o;
        return Objects.equals(getApartmentHouse(), that.getApartmentHouse())
                && Objects.equals(getApartmentNumber(), that.getApartmentNumber())
                && Objects.equals(getFloor(), that.getFloor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getApartmentHouse(), getApartmentNumber(), getFloor());
    }

}
