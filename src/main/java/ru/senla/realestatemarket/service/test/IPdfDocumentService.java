package ru.senla.realestatemarket.service.test;

import org.springframework.web.multipart.MultipartFile;
import ru.senla.realestatemarket.model.test.PdfDocument;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IPdfDocumentService extends IAbstractService<PdfDocument, Long> {

    List<PdfDocument> getPdfDocumentsOfCurrentUser();
    PdfDocument getPdfDocumentByIdOfCurrentUser(Long id);

    PdfDocument addFromCurrentUser(MultipartFile file);

}
