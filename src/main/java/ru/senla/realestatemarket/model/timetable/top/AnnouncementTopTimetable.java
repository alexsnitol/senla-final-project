package ru.senla.realestatemarket.model.timetable.top;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "announcement_top_timetables")
public class AnnouncementTopTimetable {

    @Id
    @SequenceGenerator(name = "seq_announcement_top_timetables", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_announcement_top_timetables")
    private Long id;

    @Column(name = "from_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime fromDt;

    @Column(name = "to_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime toDt;

}
