package ru.senla.realestatemarket.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family_house_announcements")
public class FamilyHouseAnnouncement extends HousingAnnouncement {

    @ManyToOne
    @JoinColumn(name = "family_house_property_id")
    private FamilyHouseProperty property;

    @Formula("(" +
            " SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END" +
            " FROM family_house_announcement_top_timetables AS t" +
            " WHERE t.from_date_time <= now() AND t.to_date_time >= now() AND t.family_house_announcement_id = id" +
            ")")
    private Boolean top;

}
