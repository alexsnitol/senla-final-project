package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.timetable.Timetable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class AnnouncementTopTimetable extends Timetable implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_announcement_top_timetables", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_announcement_top_timetables")
    private Long id;


    public AnnouncementTopTimetable(LocalDateTime fromDt, LocalDateTime toDt) {
        super(fromDt, toDt);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnnouncementTopTimetable)) return false;
        if (!super.equals(o)) return false;
        AnnouncementTopTimetable that = (AnnouncementTopTimetable) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

}
