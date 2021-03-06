package rest.bibliopole.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;
import rest.bibliopole.model.Book;
import rest.bibliopole.model.dto.BookReqDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.mapper.BookMapper;

import java.util.ArrayList;
import java.util.List;

@Profile({"h2"})
@Component
public class DemoData {

    private final BookRepository repository;

    @Autowired
    public DemoData(BookRepository repository) {
        this.repository = repository;
    }

    public static final Book BOOK1 = new Book("Кобзарь", "Тарас Шевченко", "Украинские сувениры", 2021, 500, 5018d);
    public static final Book BOOK2 = new Book("Хребты безумия", "Говард Лавкрафт", "Азбука", 2020, 64, 333.51);
    public static final Book BOOK3 = new Book("Книга власти", "Шан Ян", "Бука", 1999, 312, 51.99d);
    public static final Book BOOK4 = new Book("Облачный атлас", "Дэвид Митчелл", "Укркнига", 1998, 544, 100d);
    public static final Book BOOK5 = new Book("Ктулху", "лавкрафт", "Укркнига", 1995, 101, 13d);

    public static final List<Book> books = List.of(BOOK1, BOOK2, BOOK3, BOOK4, BOOK5);

    public void populate() {
        repository.saveAll(books);
    }
}
