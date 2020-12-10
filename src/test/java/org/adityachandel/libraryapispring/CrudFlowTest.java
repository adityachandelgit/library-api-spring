package org.adityachandel.libraryapispring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adityachandel.libraryapispring.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CrudFlowTest {

    @Autowired
    private MockMvc mockMvc;

    private String authToken = "Basic YWRpdHlhOkdXTURTc1BOWlg2TjlSNm1CblpzdHdMQTQ1N2NtUE1m";

    @Test
    public void crudFlowTest() throws Exception {

        // Initial there won't be any books in the database to the returned list would be empty
        doGet("/api/library/book/all")
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[*]").isEmpty());

        Book book = new Book(1, "Chamber of Secrets", "JKR");

        // Create book
        MvcResult mvcResult = doPost("/api/library/book", new ObjectMapper().writeValueAsString(book))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        final Book bookCreated = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertEquals(book.getId(), bookCreated.getId());
        assertEquals(book.getAuthor(), bookCreated.getAuthor());
        assertEquals(book.getName(), bookCreated.getName());

        // Get all Books, now there should be 1 book in the database.
        mvcResult = doGet("/api/library/book/all")
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        final List<Book> books = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Book>>(){});
        assertEquals(1, books.size());

        // Get Book by id
        mvcResult = doGet("/api/library/book/1")
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Book bookReturned = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertEquals(book.getId(), bookReturned.getId());
        assertEquals(book.getAuthor(), bookReturned.getAuthor());
        assertEquals(book.getName(), bookReturned.getName());

        // Update book
        Book bookUpdate = new Book(1, "Prisoner of Azkaban", "JKR");
        mvcResult = doPut("/api/library/book", new ObjectMapper().writeValueAsString(bookUpdate))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        final Book bookUpdated = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertEquals(bookUpdate.getId(), bookUpdated.getId());
        assertEquals(bookUpdate.getAuthor(), bookUpdated.getAuthor());
        assertEquals(bookUpdate.getName(), bookUpdated.getName());

        // Get Book by id, should return updated book
        mvcResult = doGet("/api/library/book/1")
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        bookReturned = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertEquals(bookUpdate.getId(), bookReturned.getId());
        assertEquals(bookUpdate.getAuthor(), bookReturned.getAuthor());
        assertEquals(bookUpdate.getName(), bookReturned.getName());


        // Get Book by id, should return updated book
        doDelete("/api/library/book/1")
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // After deletion there won't be any books in the databas.
        doGet("/api/library/book/all")
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[*]").isEmpty());
    }


    private ResultActions doGet(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }

    private ResultActions doPost(String url, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }

    private ResultActions doPut(String url, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .put(url)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }

    private ResultActions doDelete(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .delete(url)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }
}
