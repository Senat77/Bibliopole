package rest.bibliopole.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import rest.bibliopole.model.Book;
import rest.bibliopole.model.dto.BookReqDTO;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.mapper.BookMapper;
import rest.bibliopole.util.exception.EntityNotFoundException;
import rest.bibliopole.util.exception.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BookMapper mapper;

    private final BookRepository repository;

    @Autowired
    public BookService(BookMapper mapper, BookRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<BookRespDTO> getAll() {
        return mapper.fromBooks(repository.findAll());
    }

    public BookRespDTO get(Integer id) throws EntityNotFoundException {
        return mapper.fromBook(findById(id));
    }

    private Book findById (Integer id) throws EntityNotFoundException {
        Optional<Book> book = repository.findById(id);
        if(book.isPresent())
            return book.get();
        else
            throw new EntityNotFoundException();
    }

    @Transactional
    public BookRespDTO create(BookReqDTO bookDTO) throws EntityAlreadyExistsException {
        Assert.notNull(bookDTO, "Book must not be null");
        if(repository.findByNameAndAuthorAndPublishingAndYear(bookDTO.getName(), bookDTO.getAuthor(),
                bookDTO.getPublishing(), bookDTO.getYear()) != null)
            throw new EntityAlreadyExistsException();
        Book book = mapper.toBook(bookDTO);
        repository.save(book);
        log.info("Book created : {}", book);
        return mapper.fromBook(book);
    }

    @Transactional
    public void delete (int id) throws EntityNotFoundException {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new EntityNotFoundException();
    }

    @Transactional
    public BookRespDTO update(BookReqDTO bookDTO) throws EntityNotFoundException {
        Assert.notNull(bookDTO, "Book must not be null");
        Book book = findById(bookDTO.getId());
        mapper.toUpdate(book,bookDTO);
        log.debug("[i] Book with id={} updated : {}", book.getId(), book);
        return mapper.fromBook(book);
    }

    @Transactional
    public BookRespDTO newCost(Integer id, Double percent) throws EntityNotFoundException {
        Book book = findById(id);
        book.setCost(book.getCost() + book.getCost() / 100 * percent);
        return mapper.fromBook(book);
    }

    public List<BookRespDTO> filter(String name, String author, String publishing, Integer year) {
        return mapper
                .fromBooks(repository
                        .findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishingContainingIgnoreCaseAndYearGreaterThanEqual(name, author, publishing, year));
    }
}
