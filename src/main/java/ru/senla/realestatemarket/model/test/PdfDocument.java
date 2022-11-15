package ru.senla.realestatemarket.model.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pdf_documents")
public class PdfDocument implements IModel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @Column(name = "file_name")
    private String fileName;
    @Lob
    @JsonIgnore
    private byte[] data;
    private String size;

}
