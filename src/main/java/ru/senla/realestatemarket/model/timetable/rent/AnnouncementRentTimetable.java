package ru.senla.realestatemarket.model.timetable.rent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.Column;
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
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class AnnouncementRentTimetable implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_announcement_rent_timetables", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_announcement_rent_timetables")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_tenant")
    private User tenant;

    @Column(name = "from_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime fromDt;

    @Column(name = "to_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime toDt;

}
