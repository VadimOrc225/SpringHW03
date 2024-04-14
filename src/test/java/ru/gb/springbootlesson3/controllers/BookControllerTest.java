package ru.gb.springbootlesson3.controllers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.repository.JpaBookRepository;
import ru.gb.springbootlesson3.services.BookService;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class BookControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    JpaBookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void setUp() {
//        bookService.createBook("Иду на грозу");
//        bookService.createBook("Зубр");

        webTestClient = WebTestClient.bindToController(new BookController(bookService))
                .configureClient()
                .baseUrl("/book")
                .build();
    }



    @Test
    void deleteWhenExist() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from book", Long.class);
        if (maxId == null) throw new RuntimeException("в базе данных не оказалось записей для теста...");

        webTestClient.delete()
                .uri("/delete/" + maxId)
                .exchange()
                .expectStatus().isOk();
        Long unexpectedId = jdbcTemplate.queryForObject("select count(*) from book where id = ?", Long.class, maxId);

        assertTrue(unexpectedId != null && unexpectedId == 0);
    }
    @Test
    void deleteWhenNotExists() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from book", Long.class);
        if (maxId == null) throw new RuntimeException("в базе данных не оказалось записей для теста...");
        long unexpectedId = maxId + 20;

        webTestClient.delete()
                .uri("/delete/" + unexpectedId)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void testSave() {
        String name = "Книга джунглей";


        Book savedBook = webTestClient.post()
                .bodyValue(name)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        assertEquals(name, savedBook.getName());

    }

    @Test
    void testGetById() {
        String name = "test";
        bookService.createBook(name);
        long id = bookService.findByName(name).getId();
        Book findedBook = webTestClient.get()
                .uri("/" + id)

                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(findedBook);
        assertEquals(id, findedBook.getId());
        assertEquals(name, findedBook.getName());
    }

    @Test
    void testGetAll() {
        List<Book> bookList = bookService.getAllBooks();

        List<Book> responseBody = webTestClient.get()
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Book>>() {
                })
                .returnResult()
                .getResponseBody();

        assertEquals(bookList.size(), responseBody.size());
        for (Book book : responseBody) {
            boolean found = bookList
                    .stream()
                    .filter(it -> Objects.equals(book.getId(), it.getId()))
                    .anyMatch(it -> Objects.equals(book.getName(), it.getName()));

            assertTrue(found);
        }
    }
}
