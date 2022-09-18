package ru.senla.realestatemarket.model.timetable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Timetable {

    @Column(name = "from_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime fromDt;

    @Column(name = "to_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime toDt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timetable)) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(getFromDt(), timetable.getFromDt())
                && Objects.equals(getToDt(), timetable.getToDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromDt(), getToDt());
    }

}
