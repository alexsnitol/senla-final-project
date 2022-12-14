package ru.senla.realestatemarket.model.timetable.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.timetable.Timetable;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class AnnouncementRentTimetable extends Timetable implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_announcement_rent_timetables", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_announcement_rent_timetables")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_tenant")
    private User tenant;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnouncementRentTimetable)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AnnouncementRentTimetable that = (AnnouncementRentTimetable) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTenant(), that.getTenant());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getTenant());
    }

}
