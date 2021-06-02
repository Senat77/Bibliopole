package rest.bibliopole.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import rest.bibliopole.model.Book;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.mapper.BookMapper;
import rest.bibliopole.service.mapper.BookMapperImpl;
import rest.bibliopole.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@Profile("test")
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @TestConfiguration
    static class RestaurantServiceTestContextConfiguration {

        @Bean
        public BookService restaurantService() {
            return new BookService();
        }

        @Bean
        public BookMapper restaurantMapper() {
            return new BookMapperImpl();
        }
    }

    @Autowired
    private BookService service;

    @Autowired
    private BookMapper mapper;

    @MockBean
    private BookRepository repository;

    @Before
    public void setup() {
        Book book1 = new Book(1,"Book1", "Author1", "Publishing1", 2001, 100, 100.0);
        Book book2 = new Book(2,"Book2", "Author2", "Publishing2", 2002, 200, 200.0);
        Book book3 = new Book(3,"Book3", "Author1", "Publishing2", 2003, 300, 300.0);

        Book book4 = new Book(4,"Book4", "Author4", "Publishing4", 2004, 400, 400.0);

        List<Book> allBooks = List.of(book1, book2, book3);

        Mockito.when(repository.findAll()).thenReturn(allBooks);
        Mockito.when(repository.findById(2)).thenReturn(Optional.of(book2));
        Mockito.when(repository.save(book4)).thenReturn(book4);
    }

    @Test
    public void getAll() {
        List<BookRespDTO> books = service.getAll();
        assertThat(books).hasSize(3);
    }

    @Test
    public void get() throws EntityNotFoundException {
        BookRespDTO book = service.get(2);
        assertNotNull(book);
        assertThat(book.getYear()).isEqualTo(2002);
    }

    @Test
    public void newCost() throws EntityNotFoundException {
        BookRespDTO bookDTO = service.newCost(2, -10.0);
        assertNotNull(bookDTO);
        assertThat(bookDTO.getCost()).isEqualTo(180.0);
    }
}