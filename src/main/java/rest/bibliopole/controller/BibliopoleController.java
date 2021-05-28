package rest.bibliopole.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.BookService;

import java.util.List;

@RestController
public class BibliopoleController {

    private final BookService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/books";

    @Autowired
    public BibliopoleController(BookService service, BookRepository repository) {
        this.service = service;
    }

    @GetMapping
    public List<BookRespDTO> getAll() {
        log.info("Get all books");
        return service.getAll();
    }
}
