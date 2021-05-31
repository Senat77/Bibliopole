package rest.bibliopole.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rest.bibliopole.model.dto.BookReqDTO;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.BookService;
import rest.bibliopole.util.exception.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping(value = BibliopoleRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class BibliopoleRestController {

    private final BookService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/books";

    @Autowired
    public BibliopoleRestController(BookService service, BookRepository repository) {
        this.service = service;
    }

    @GetMapping
    public List<BookRespDTO> getAll() {
        log.info("Get all books");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookRespDTO get(@PathVariable("id") Integer id) throws EntityNotFoundException {
        log.info("get restaurant with id = {}", id);
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create (@Validated(BookReqDTO.New.class) @RequestBody BookReqDTO bookDTO) {
        log.info("Create book {}", bookDTO);
        return new ResponseEntity<> (service.create(bookDTO), HttpStatus.CREATED);
    }
}
