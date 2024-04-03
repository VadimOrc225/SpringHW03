package ru.gb.springbootlesson3.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.springbootlesson3.services.PersonService;


@RestController
    @RequiredArgsConstructor
    public class PersonController {

        private final PersonService service;

        @GetMapping("any")
        public String any(){
            return "any";
        }

        @GetMapping("admin")
        public String admin(){
            return "admin";
        }

        @GetMapping("user")
        public String user(){
            return "user";
        }

        @GetMapping("auth")
        public String auth(){
            return "auth";
        }
    }