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
import rest.bibliopole.util.exception.EntityAlreadyExistsException;
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
        log.info("Get book with id = {}", id);
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create (@Validated(BookReqDTO.New.class) @RequestBody BookReqDTO bookDTO)
            throws EntityAlreadyExistsException {
        log.info("Create book {}", bookDTO);
        return new ResponseEntity<> (service.create(bookDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable ("id") Integer id) throws EntityNotFoundException {
        log.info("Delete book id = {}", id);
        service.delete(id);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookRespDTO update (@RequestBody BookReqDTO bookDTO,
                               @PathVariable("id") Integer id) throws EntityNotFoundException {
        log.info("Update book id = {} with data = {}", id, bookDTO);
        bookDTO.setId(id);
        return service.update(bookDTO);
    }

    @PatchMapping(value = "/new_cost/{id}")
    public BookRespDTO newCost(@RequestParam(value = "percent", required = true) Double percent,
                               @PathVariable("id") Integer id) throws EntityNotFoundException {
        log.info("New cost for book id = {} , percent = {}", id, percent);
        return service.newCost(id, percent);
    }

    @GetMapping(value = "/filter")
    public List<BookRespDTO> filter(@RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "author", defaultValue = "") String author,
                                    @RequestParam(value = "publishing", defaultValue = "") String publishing,
                                    @RequestParam(value = "year", defaultValue = "868") Integer year) {
        log.info("Filter for name = {}, author = {}, publishing = {}, year >= {}", name, author, publishing, year);
        return service.filter(name, author, publishing, year);
    }
}
