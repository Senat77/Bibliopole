package rest.bibliopole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.bibliopole.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishingContainingIgnoreCaseAndYearGreaterThanEqual (
            String name,
            String author,
            String publishing,
            Integer year);
}
