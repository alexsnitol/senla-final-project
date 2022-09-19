package ru.senla.realestatemarket.model.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "announcement_top_prices")
public class AnnouncementTopPrice implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_announcement_top_prices", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_announcement_top_prices")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyTypeEnum propertyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "announcement_type")
    private AnnouncementTypeEnum announcementType;

    @Column(name = "price_per_hour")
    private Float pricePerHour;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnouncementTopPrice)) {
            return false;
        }
        AnnouncementTopPrice that = (AnnouncementTopPrice) o;
        return Objects.equals(getId(), that.getId())
                && getPropertyType() == that.getPropertyType()
                && getAnnouncementType() == that.getAnnouncementType()
                && Objects.equals(getPricePerHour(), that.getPricePerHour());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPropertyType(), getAnnouncementType(), getPricePerHour());
    }

}
