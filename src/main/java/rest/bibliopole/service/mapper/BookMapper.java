package rest.bibliopole.service.mapper;

import org.mapstruct.Mapper;
import rest.bibliopole.model.Book;
import rest.bibliopole.model.dto.BookReqDTO;
import rest.bibliopole.model.dto.BookRespDTO;

import java.util.List;

@Mapper
public interface BookMapper {

    BookRespDTO fromBook (Book book);

    List<BookRespDTO> fromBooks (List<Book> books);

    Book toBook(BookReqDTO bookDTO);
}
