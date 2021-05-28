package rest.bibliopole.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.service.mapper.BookMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookMapper mapper;

    @Autowired
    private BookRepository repository;

    public List<BookRespDTO> getAll() {
        return mapper.fromBooks(repository.findAll());
    }
}
