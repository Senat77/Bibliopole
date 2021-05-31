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

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookMapper mapper;

    @Autowired
    private BookRepository repository;

    public List<BookRespDTO> getAll() {
        return mapper.fromBooks(repository.findAll());
    }

    public BookRespDTO get(Integer id)  throws EntityNotFoundException {
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
    public BookRespDTO create(BookReqDTO bookDTO) {
        Assert.notNull(bookDTO, "Book must not be null");
        Book book = mapper.toBook(bookDTO);
        repository.save(book);
        log.info("Book created : {}", book);
        return mapper.fromBook(book);
    }
}
