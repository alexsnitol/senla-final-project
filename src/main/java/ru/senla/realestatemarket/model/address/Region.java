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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "regions")
public class Region implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_regions", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_regions")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<City> cities;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        Region region = (Region) o;
        return Objects.equals(getId(), region.getId())
                && Objects.equals(getName(), region.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

}
