package group.bibliopole.controller;

import group.bibliopole.model.Book;
import group.bibliopole.repository.BookRepository;
import group.bibliopole.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
public class BibliopoleController {

    private final BookService service;

    private static final int RECORDS_BY_PAGE = 10;
    private static final String SORT = "name";

    private final Logger log = LoggerFactory.getLogger(getClass());

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

    @GetMapping("/book/{id}")
    public String edit(@PathVariable Long id, Model model) {
        log.info("In edit");
        model.addAttribute("book", service.getById(id));
        return "detail";
    }

    @PostMapping("/book")
    public String save(@ModelAttribute Book book) {
        service.save(book);
        return "redirect:/";
    }

    @GetMapping("/book")
    public String create(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "detail";
    }

    @GetMapping("/part/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
