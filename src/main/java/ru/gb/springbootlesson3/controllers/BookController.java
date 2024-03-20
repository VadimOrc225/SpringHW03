package ru.gb.springbootlesson3.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.services.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody String bookName){
        log.info("Поступил запрос на создание книги: " + bookName);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookName));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        log.info("Поступил запрос на выдачу информации о всеx книгах библиотеки");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id){
        log.info("Поступил запрос на выдачу информации о книге с Id: " + id);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable long id){
        final boolean deleted = bookService.deleteBook(id);
        log.info("Поступил запрос на удаление книги с Id: " + id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
