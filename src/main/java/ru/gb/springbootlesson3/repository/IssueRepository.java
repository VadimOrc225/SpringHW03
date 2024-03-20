package ru.gb.springbootlesson3.repository;


import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IssueRepository {
    private List<Issue> issues = new ArrayList<>();

    public Issue returnIssue(long id){
        Issue issue = issues.stream().filter(issue1 -> issue1.getId() == id).findFirst().orElse(null);

        if(issue != null) {
            issue.setTime2(LocalDateTime.now());
        }
        return issue;
    }

    public void createIssue(Issue issue) {
        issues.add(issue);
    }

    public Issue getById(long id){
        return issues.stream().filter(issue -> issue.getId() == id).findFirst().orElse(null);
    }
    public List<Issue> getAllIssues(){
        return issues;
    }


}
