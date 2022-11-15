package ru.senla.realestatemarket.repo.test.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.test.PdfDocument;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import javax.persistence.criteria.Join;

public class PdfDocumentSpecification {

    public static Specification<PdfDocument> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }

    public static Specification<PdfDocument> hasUserId(Long userId) {
        return ((root, query, criteriaBuilder) -> {
            Join<PdfDocument, User> userJoin = root.join("user");

            return criteriaBuilder.equal(userJoin.get("id"), userId);
        });
    }

}
