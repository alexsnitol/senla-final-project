package ru.senla.realestatemarket.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import ru.senla.realestatemarket.model.IModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_users", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    private Long id;

    private String username;

    private String password;

    private Boolean enabled = true;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String patronymic;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Double balance = 0D;

    @Column(name = "created_date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdDt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private List<Role> roles;

    @Formula("(SELECT AVG(r.note) FROM reviews AS r WHERE r.user_id_seller = id)")
    private Float rating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<BalanceOperation> balanceOperations;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(getUsername(), user.getUsername())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getEnabled(), user.getEnabled())
                && Objects.equals(getLastName(), user.getLastName())
                && Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(getPatronymic(), user.getPatronymic())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getPhoneNumber(), user.getPhoneNumber())
                && Objects.equals(getCreatedDt(), user.getCreatedDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEnabled(),
                getLastName(), getFirstName(), getPatronymic(), getEmail(), getPhoneNumber(),
                getCreatedDt());
    }
}
