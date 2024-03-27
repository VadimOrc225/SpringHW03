package ru.gb.springbootlesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springbootlesson3.entity.Issue;

public interface JpaIssueRepository extends JpaRepository<Issue, Long> {
}
