package ru.senla.realestatemarket.model.purchase.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family_house_announcement_rent_purchases")
public class FamilyHouseAnnouncementRentPurchase extends AnnouncementRentPurchase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_house_announcement_rent_timetable_id")
    private FamilyHouseAnnouncementRentTimetable timetable;

}
