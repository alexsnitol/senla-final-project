package ru.senla.realestatemarket.model.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.IModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "renovation_types")
public class RenovationType implements IModel<Long> {

    @Id
    @SequenceGenerator(name = "seq_renovation_types", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_renovation_types")
    private Long id;

    private String name;

}
