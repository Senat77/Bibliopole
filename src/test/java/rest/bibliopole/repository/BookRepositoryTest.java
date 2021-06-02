package rest.bibliopole.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import rest.bibliopole.model.Book;
import rest.bibliopole.util.DemoData;

import static org.junit.Assert.*;

import java.util.List;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/testdata/books.sql"})})
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    public void findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishingContainingIgnoreCaseAndYearGreaterThanEqual() {

        List<Book> books = repository.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishingContainingIgnoreCaseAndYearGreaterThanEqual(
                "",
                "крАфт",
                "",
                868
        );

        assertNotNull(books);
        assertEquals(books.size(), 2);
        assertTrue(books.containsAll(List.of(DemoData.BOOK2, DemoData.BOOK2)));
    }

    @Test
    public void findByNameAndAuthorAndPublishingAndYear() {

        Book book = repository.findByNameAndAuthorAndPublishingAndYear("Книга власти", "Шан Ян", "Бука", 1999);
        assertNotNull(book);
        assertEquals(book, DemoData.BOOK3);
        assertNotEquals(book, DemoData.BOOK4);
    }
}