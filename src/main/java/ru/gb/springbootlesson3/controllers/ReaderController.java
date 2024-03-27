package ru.gb.springbootlesson3.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.services.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reader")
@Slf4j
public class ReaderController {
    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("{id}/issue")
    public ResponseEntity<List<Issue>> getIssuesForReader(@PathVariable long id){
        log.info("Поступил запрос на информацию о всех выдачах книг пользователю {}",
                readerService.getReaderById(id));
        try {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.getIssuesForReader(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Reader> createReader(@RequestBody ReaderRequest readerRequest){
        log.info("Поступил запрос на создание пользователя: " +
                readerRequest.getName());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(readerService.createReader(
                    readerRequest.getName()));

        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders(){
        log.info("Поступил запрос на выдачу информации о всех пользователей");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.getAllReaders());
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable long id){
        log.info("Поступил запрос на выдачу информации о пользователе с Id: " + readerService.getReaderById(id));

        Reader reader = readerService.getReaderById(id);
        if(reader == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reader, HttpStatus.OK);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReaderById(@PathVariable long id) {

        log.info("Поступил запрос на удаление пользователя с Id: " + id);

        try {
            readerService.deleteReader(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}