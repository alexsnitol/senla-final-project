package ru.senla.realestatemarket.controller.test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import ru.senla.realestatemarket.dto.OneStringParamDto;
import ru.senla.realestatemarket.model.test.PdfDocument;
import ru.senla.realestatemarket.service.test.IPdfDocumentService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/api/test")
@RestController
public class TestController {

    private final IPdfDocumentService pdfDocumentService;


    public TestController(IPdfDocumentService pdfDocumentService) {
        this.pdfDocumentService = pdfDocumentService;
    }


    @GetMapping("/sessions")
    public ResponseEntity<Set<String>> getSessionOfCurrentUser() {
        Jedis jedis = new Jedis("localhost", 6379);
        Set<String> result = jedis.keys("*");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

@PostMapping("/users/current/theme")
public ResponseEntity<String> setThemeOfCurrentUser(
        HttpServletResponse response, @RequestBody OneStringParamDto theme
) {
    Cookie cookie = new Cookie("theme", theme.getValue());
    cookie.setPath("/");

    response.addCookie(cookie);

    return ResponseEntity.ok("Theme " + theme.getValue() + " accepted");
}

@PostMapping("/users/current/language")
public ResponseEntity<String> setLanguageOfCurrentUser(
        HttpServletResponse response, @RequestBody OneStringParamDto language
) {
    Cookie cookie = new Cookie("language", language.getValue());
    cookie.setPath("/");

    response.addCookie(cookie);

    return ResponseEntity.ok("Language " + language.getValue() + " accepted");
}

    @GetMapping("/users/current/documents")
    public ResponseEntity<List<PdfDocument>> getAllDocumentsOfCurrentUser() {
        return ResponseEntity.ok(pdfDocumentService.getPdfDocumentsOfCurrentUser());
    }

    @GetMapping("/users/current/documents/{id}")
    public ResponseEntity<byte[]> getDocumentByIdOfCurrentUser(@PathVariable Long id) {
        PdfDocument pdfDocument = pdfDocumentService.getPdfDocumentByIdOfCurrentUser(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + pdfDocument.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(pdfDocument.getData());
    }

    @PostMapping("/users/current/documents")
    public ResponseEntity<PdfDocument> addDocumentFromCurrentUser(
            @RequestParam MultipartFile pdfDocument
    ) throws Exception {
        String filename = pdfDocument.getResource().getFilename();

        String extension = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1)).get();

        if (extension.equals("pdf")) {
            return new ResponseEntity<>(pdfDocumentService.addFromCurrentUser(pdfDocument), HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("You may upload only PDF document.");
        }
    }

}
