package ru.senla.realestatemarket.model.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_cities", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cities")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Region region;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Street> streets;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return Objects.equals(getId(), city.getId())
                && Objects.equals(getName(), city.getName())
                && Objects.equals(getRegion(), city.getRegion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRegion());
    }

}
