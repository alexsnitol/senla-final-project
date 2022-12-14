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

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_messages", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_messages")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id_sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "user_id_receiver")
    private User receiver;

    private String text;

    @Column(name = "created_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdDt = LocalDateTime.now();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId())
                && Objects.equals(getSender(), message.getSender())
                && Objects.equals(getReceiver(), message.getReceiver())
                && Objects.equals(getText(), message.getText())
                && Objects.equals(getCreatedDt(), message.getCreatedDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSender(), getReceiver(), getText(), getCreatedDt());
    }

}
