package rest.bibliopole.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.BookService;

import java.util.List;

@RestController
@RequestMapping(value = BibliopoleRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class BibliopoleRestController {

    private final BookService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "";

    @Autowired
    public BibliopoleRestController(BookService service, BookRepository repository) {
        this.service = service;
    }

    @GetMapping
    public List<BookRespDTO> getAll() {
        log.info("Get all books");
        return service.getAll();
    }
}
