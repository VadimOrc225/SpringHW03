package ru.gb.springbootlesson3.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.services.BookService;
import ru.gb.springbootlesson3.services.ReaderService;

import java.util.List;

@Controller
@RequestMapping("/ui")
@Slf4j
public class UiReaderController {

    private final ReaderService readerService;

    @Autowired
    public UiReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/readers")
    public String getAllBooks(Model model){
        List<Reader> readers = readerService.getAllReaders();
        model.addAttribute("readers", readers);
        return "allReaders";
    }

}