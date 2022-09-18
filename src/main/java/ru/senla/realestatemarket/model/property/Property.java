package ru.senla.realestatemarket.model.property;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.user.User;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Property implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_properties", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_properties")
    private Long id;

    private Float area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    private PropertyStatusEnum status = PropertyStatusEnum.ACTIVE;

    @Transient
    @Setter(AccessLevel.PROTECTED)
    private PropertyTypeEnum propertyType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        Property property = (Property) o;
        return Objects.equals(getId(), property.getId())
                && Objects.equals(getArea(), property.getArea())
                && Objects.equals(getOwner(), property.getOwner())
                && getStatus() == property.getStatus()
                && getPropertyType() == property.getPropertyType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArea(), getOwner(), getStatus(), getPropertyType());
    }
}
