package ru.senla.realestatemarket.repo.test;

import ru.senla.realestatemarket.model.test.PdfDocument;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IPdfDocumentRepository extends IAbstractRepository<PdfDocument, Long> {

    List<PdfDocument> findAllByUserId(Long userId);
    PdfDocument findByIdAndByUserId(Long id, Long userId);

}
