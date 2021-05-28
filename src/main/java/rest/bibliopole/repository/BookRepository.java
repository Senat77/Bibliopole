package rest.bibliopole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.bibliopole.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
