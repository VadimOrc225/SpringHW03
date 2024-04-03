package ru.gb.springbootlesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springbootlesson3.entity.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByLogin(String login);
}
