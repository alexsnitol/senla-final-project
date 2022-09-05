package ru.senla.realestatemarket.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
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

}
