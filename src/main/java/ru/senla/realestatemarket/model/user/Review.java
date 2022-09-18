package ru.senla.realestatemarket.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_notes", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notes")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id_customer")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "user_id_seller")
    private User seller;

    private String comment;

    private Short note;

    @Column(name = "created_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdDt = LocalDateTime.now();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(getId(), review.getId())
                && Objects.equals(getCustomer(), review.getCustomer())
                && Objects.equals(getSeller(), review.getSeller())
                && Objects.equals(getComment(), review.getComment())
                && Objects.equals(getNote(), review.getNote())
                && Objects.equals(getCreatedDt(), review.getCreatedDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomer(), getSeller(), getComment(), getNote(), getCreatedDt());
    }

}
