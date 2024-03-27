package ru.gb.springbootlesson3.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "reader")
public class Reader {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    public Reader() {

    }

    public Reader(String name) {

        this.name = name;
    }
}
