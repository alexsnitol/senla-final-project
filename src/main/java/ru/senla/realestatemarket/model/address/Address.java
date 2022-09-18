package ru.senla.realestatemarket.model.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_addresses", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_addresses")
    private Long id;

    @Transient
    @JsonManagedReference
    private Region region;

    @Transient
    @JsonManagedReference
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    private Street street;

    @Column(name = "house_number")
    private String houseNumber;


    @PostLoad
    public void initLoad() {
        City city = street.getCity();
        Region region = city.getRegion();

        this.region = region;
        this.city = city;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(getId(), address.getId())
                && Objects.equals(getStreet(), address.getStreet())
                && Objects.equals(getHouseNumber(), address.getHouseNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStreet(), getHouseNumber());
    }

}
