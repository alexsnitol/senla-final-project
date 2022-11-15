package ru.senla.realestatemarket.service.test.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.senla.realestatemarket.exception.BusinessRuntimeException;
import ru.senla.realestatemarket.model.test.PdfDocument;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.test.IPdfDocumentRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.test.IPdfDocumentService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class PdfDocumentServiceImpl extends AbstractServiceImpl<PdfDocument, Long> implements IPdfDocumentService {

    private final IPdfDocumentRepository pdfDocumentRepository;
    private final IUserRepository userRepository;
    private final UserUtil userUtil;


    public PdfDocumentServiceImpl(
            IPdfDocumentRepository pdfDocumentRepository,
            IUserRepository userRepository,
            UserUtil userUtil
    ) {
        this.pdfDocumentRepository = pdfDocumentRepository;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Override
    public void init() {
        setClazz(PdfDocument.class);
        setDefaultRepository(pdfDocumentRepository);
    }


    @Override
    @Transactional
    public List<PdfDocument> getPdfDocumentsOfCurrentUser() {
        return pdfDocumentRepository.findAllByUserId(userUtil.getCurrentUserId());
    }

    @Override
    @Transactional
    public PdfDocument getPdfDocumentByIdOfCurrentUser(Long id) {
        return pdfDocumentRepository.findByIdAndByUserId(id, userUtil.getCurrentUserId());
    }

    @Override
    @Transactional
    public PdfDocument addFromCurrentUser(MultipartFile file) {
        PdfDocument pdfDocument = new PdfDocument();

        User currentUser = userRepository.findById(userUtil.getCurrentUserId());

        try {
            pdfDocument.setUser(currentUser);
            pdfDocument.setFileName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            pdfDocument.setData(file.getBytes());
            pdfDocument.setSize(file.getSize()/1024 + " KB");
        } catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }

        pdfDocumentRepository.create(pdfDocument);

        return pdfDocument;
    }

}
