package group.bibliopole.repository;

import group.bibliopole.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query (
            "select b from Book b " +
                    "where (lower(b.name) like concat ('%', lower(?1), '%') or " +
                    "lower(b.author) like concat ('%', lower(?1), '%') or " +
                    "lower(b.publishing) like concat ('%', lower(?1), '%')) and " +
                    "b.year >= ?2"
    )
    Page<Book> applyFilter(String filter, Integer year, Pageable pageable);

    Book getById (long id);
}
