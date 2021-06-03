package rest.bibliopole.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rest.bibliopole.model.dto.BookReqDTO;
import rest.bibliopole.model.dto.BookRespDTO;
import rest.bibliopole.util.DemoData;
import rest.bibliopole.util.exception.EntityNotFoundException;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/testdata/books.sql"})})
public class BibliopoleRestControllerTest extends AbstractTestRestController {

    @Test
    public void getAll() throws Exception {
        mvc.perform(get(BibliopoleRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name", is("Кобзарь")))
                .andExpect(jsonPath("$[4].name", is("Ктулху")));
    }

    @Test
    public void getBook() throws Exception {
        mvc.perform(get(BibliopoleRestController.REST_URL + "/100003")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is("Дэвид Митчелл")));
    }

    @Test
    @ExceptionHandler(EntityNotFoundException.class)
    public void getBook_EntityNotFoundException() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(BibliopoleRestController.REST_URL + "/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("HTTP status\":\"NOT_FOUND"));
        assertTrue(content.contains("Message\":\"Entity not found exception"));
    }

    @Test
    public void create() throws Exception {
        BookReqDTO bookReqDTO = new BookReqDTO("Новая книга", "Новый автор", "Новое издательство",
                2000, 1000, 1000.0);
        String inputJson = super.mapToJson(bookReqDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(BibliopoleRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(inputJson))
                .andExpect(status().isCreated())
                .andReturn();
        BookRespDTO bookRespDTO = mapFromJson(getResultContent(mvcResult), BookRespDTO.class);
        assertEquals(bookRespDTO.getName(), "Новая книга");
        assertEquals(2000, (int) bookRespDTO.getYear());
        assertEquals(100005, (int) bookRespDTO.getId());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(BibliopoleRestController.REST_URL + "/100001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        BookReqDTO bookReqDTO = new BookReqDTO(DemoData.BOOK2.getName(),
                DemoData.BOOK2.getAuthor(),
                DemoData.BOOK2.getPublishing(),
                DemoData.BOOK2.getYear(),
                DemoData.BOOK2.getPages(),
                DemoData.BOOK2.getCost());
        bookReqDTO.setPublishing("Новое издательство");
        String inputJson = super.mapToJson(bookReqDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(BibliopoleRestController.REST_URL + "/100002")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().isOk())
                .andReturn();
        BookRespDTO bookRespDTO = mapFromJson(getResultContent(mvcResult), BookRespDTO.class);
        assertEquals(bookRespDTO.getName(), DemoData.BOOK2.getName());
        assertEquals(bookRespDTO.getPublishing(), "Новое издательство");
    }

    @Test
    public void newCost() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(BibliopoleRestController.REST_URL
                + "/new_cost/100003")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("percent", "-10.0"))
                .andExpect(status().isOk())
                .andReturn();
        BookRespDTO bookRespDTO = mapFromJson(getResultContent(mvcResult), BookRespDTO.class);
        assertTrue(90.0 == bookRespDTO.getCost());
    }

    @Test
    public void filter() throws Exception {
        mvc.perform(get(BibliopoleRestController.REST_URL + "/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .param("author", "лав")
                .param("year", "2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Хребты безумия")))
                .andExpect(jsonPath("$[0].author", is("Говард Лавкрафт")))
                .andExpect(jsonPath("$[0].year", is(2020)));
    }

    private String getResultContent (MvcResult result) throws UnsupportedEncodingException {
        MockHttpServletResponse response = result.getResponse();
        response.setCharacterEncoding("UTF-8");
        return response.getContentAsString();
    }
}