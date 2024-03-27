package ru.gb.springbootlesson3.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name = "issue")
@Data
public class Issue {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_id")
    private long idBook;

    @Column(name = "reader_id")
    private long idReader;

    @Column(name = "issued_at")
    private LocalDateTime time1;

    @Column(name = "returned_at")
    private LocalDateTime time2; // возврат

    public Issue() {

    }

    public Issue(long idReader, long idBook){

        this.idBook = idBook;
        this.idReader = idReader;
        this.time1 = LocalDateTime.now();

    }
}
