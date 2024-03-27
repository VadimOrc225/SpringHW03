package ru.gb.springbootlesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springbootlesson3.entity.Reader;

public interface JpaReaderRepository extends JpaRepository<Reader, Long> {
}
