package ru.senla.realestatemarket.model.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Announcement implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_announcements", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_announcements")
    private Long id;

    private Double price;

    private String description;

    @Column(name = "created_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdDt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private AnnouncementStatusEnum status = AnnouncementStatusEnum.HIDDEN;

    @Column(name = "closed_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime closedDt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcement)) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getCreatedDt(), that.getCreatedDt())
                && getStatus() == that.getStatus()
                && Objects.equals(getClosedDt(), that.getClosedDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getDescription(), getCreatedDt(), getStatus(), getClosedDt());
    }

}
