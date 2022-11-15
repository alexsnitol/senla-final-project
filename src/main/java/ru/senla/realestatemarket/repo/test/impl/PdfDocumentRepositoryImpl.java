package ru.senla.realestatemarket.repo.test.impl;

import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.test.PdfDocument;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.test.IPdfDocumentRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.senla.realestatemarket.repo.test.specification.PdfDocumentSpecification.hasId;
import static ru.senla.realestatemarket.repo.test.specification.PdfDocumentSpecification.hasUserId;

@Repository
public class PdfDocumentRepositoryImpl
        extends AbstractRepositoryImpl<PdfDocument, Long>
        implements IPdfDocumentRepository {

    @PostConstruct
    public void init() {
        setClazz(PdfDocument.class);
    }


    @Override
    public List<PdfDocument> findAllByUserId(Long userId) {
        return findAll(hasUserId(userId));
    }

    @Override
    public PdfDocument findByIdAndByUserId(Long id, Long userId) {
        return findOne(hasId(id)
                .and(hasUserId(userId))
        );
    }

}
