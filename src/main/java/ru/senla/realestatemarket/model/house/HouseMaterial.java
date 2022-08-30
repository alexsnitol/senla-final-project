package ru.senla.realestatemarket.model.house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "house_materials")
public class HouseMaterial {

    @Id
    @SequenceGenerator(name = "seq_house_materials", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_house_materials")
    private Long id;

    private String name;

}
