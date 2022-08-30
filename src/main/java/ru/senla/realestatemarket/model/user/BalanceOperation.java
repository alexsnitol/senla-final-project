package ru.senla.realestatemarket.model.user;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "balance_operations")
public class BalanceOperation {

    @Id
    @SequenceGenerator(name = "seq_balance_operations", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_balance_operations")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double sum;

    @Column(name = "created_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime createdDt = LocalDateTime.now();

    private String comment;

}
