package ru.senla.realestatemarket.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.purchase.rent.ApartmentAnnouncementRentPurchase;
import ru.senla.realestatemarket.model.purchase.rent.FamilyHouseAnnouncementRentPurchase;
import ru.senla.realestatemarket.model.purchase.top.ApartmentAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "balance_operations")
public class BalanceOperation implements IModel<Long> {

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

    @OneToMany(mappedBy = "balanceOperation",
            fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ApartmentAnnouncementRentPurchase> apartmentAnnouncementRentPurchases;

    @OneToMany(mappedBy = "balanceOperation",
            fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ApartmentAnnouncementTopPurchase> apartmentAnnouncementTopPurchases;

    @OneToMany(mappedBy = "balanceOperation",
            fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FamilyHouseAnnouncementRentPurchase> familyHouseAnnouncementRentPurchases;

    @OneToMany(mappedBy = "balanceOperation",
            fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FamilyHouseAnnouncementTopPurchase> familyHouseAnnouncementTopPurchases;

}
