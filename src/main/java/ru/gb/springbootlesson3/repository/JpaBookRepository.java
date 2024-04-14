package ru.gb.springbootlesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Book;

@Repository
public interface JpaBookRepository extends JpaRepository<Book,Long>{
    Book findByName(String name);
}
