package ru.senla.realestatemarket.model.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class HousingProperty extends Property {

    @Column(name = "number_of_rooms")
    private Short numberOfRooms;

    @ManyToOne
    @JoinColumn(name = "renovation_type_id")
    private RenovationType renovationType;

}
