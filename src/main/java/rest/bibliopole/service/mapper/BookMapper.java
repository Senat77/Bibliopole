package rest.bibliopole.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rest.bibliopole.model.Book;
import rest.bibliopole.model.dto.BookReqDTO;
import rest.bibliopole.model.dto.BookRespDTO;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        nullValueCheckStrategy = ALWAYS,
        nullValueMappingStrategy = RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = IGNORE
)
public interface BookMapper {

    BookRespDTO fromBook (Book book);

    List<BookRespDTO> fromBooks (List<Book> books);

    Book toUpdate(@MappingTarget Book book, BookReqDTO bookDTO);

    Book toBook(BookReqDTO bookDTO);

    List<Book> toBooks (List<BookRespDTO> books);
}
