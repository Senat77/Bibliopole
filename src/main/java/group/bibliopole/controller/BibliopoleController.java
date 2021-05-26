package group.bibliopole.controller;

import group.bibliopole.model.Book;
import group.bibliopole.repository.BookRepository;
import group.bibliopole.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
public class BibliopoleController {

    private final BookService service;

    private static final int RECORDS_BY_PAGE = 10;
    private static final String SORT = "name";

    @Autowired
    public BibliopoleController(BookService service, BookRepository repository) {
        this.service = service;
    }

    @GetMapping("/")
    public String books (
            @RequestParam (value = "q", required = false) String query,
            Model model,
            @PageableDefault(size = RECORDS_BY_PAGE, sort = SORT, direction = ASC) Pageable pageable
    ) {
        Page<Book> books = service.applyFilter((query != null) ? query : "", pageable);
        model.addAttribute("books", books);
        model.addAttribute("query", query);

        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        return "books";
    }
}
