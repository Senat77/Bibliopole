package group.bibliopole.service;

import group.bibliopole.model.Book;
import group.bibliopole.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Page<Book> applyFilter(String filter, Integer year, Pageable pageable) {
        return repository.applyFilter(filter, year, pageable);
    }

    public Book getById(Long id) {
        return repository.getById(id);
    }

    public void save(Book book) {
        repository.save(book);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void changeCost(Long id, Float percent) {
        repository.changeCost(id, percent);
    }
}
