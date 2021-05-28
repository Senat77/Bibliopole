package rest.bibliopole.service.mapper;

import org.mapstruct.Mapper;
import rest.bibliopole.model.Book;
import rest.bibliopole.model.dto.BookRespDTO;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper
public interface BookMapper {

    BookRespDTO fromBook (Book book);

    List<BookRespDTO> fromBooks (List<Book> books);
}
