package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.springbootlesson3.controllers.IssueRequest;
import ru.gb.springbootlesson3.entity.*;
import ru.gb.springbootlesson3.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.yaml")
public class IssueService {
    //    private final BookRepository bookRepository;
//    private final IssueRepository issueRepository;
//    private final ReaderRepository readerRepository;
    private final JpaIssueRepository issueRepository;
    private final JpaReaderRepository readerRepository;
    private final JpaBookRepository bookRepository;

    @Setter
    @Value("${spring.application.issue.max_allowed_books:1}")
    private int max_allowed_books;

    public Reader getReader(long id){
        return readerRepository.findAll().stream().filter(reader -> reader.getId() == id).findFirst().orElse(null);
    }

    public List<Book> booksThatReaderHas(Reader reader){
        List<Book> books = new ArrayList<>();
        for (Issue issue : issueRepository.findAll()){
            if (issue.getIdReader() == reader.getId()){
                books.add(bookRepository.findAll().get((int) issue.getIdBook()));
            }
        }
        return books;
    }

    /**
     * Возрат книги в библиотеку
     * @param id id выдачи для изменения статуса возрата
     * @return выдыча с изменённым статусом
     */
    public Issue returnIssue(long id){
        if(issueRepository.getReferenceById(id).getTime2() == null) {
            return issueRepository.getReferenceById(id);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Книга с id:" +
                issueRepository.getReferenceById(id).getIdBook() +
                " уже была возвращена");
    }
    public Issue createIssue(IssueRequest issueRequest){
        if(bookRepository.getReferenceById(issueRequest.getBookId()) == null){
            log.info("Не удалось найти книгу: " + issueRequest.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу: " + issueRequest.getBookId());
        }
        if(readerRepository.getReferenceById(issueRequest.getReaderId()) == null){
            log.info("Не удалось найти читателя: " + issueRequest.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя: " + issueRequest.getReaderId());
        }
        if(!check(issueRequest.getReaderId())){
            log.info("У читателя с id={} превышено допустимое значение хранения книг", issueRequest.getReaderId());
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Превышено допустимое количество книг, разрешённых к выдаче");
        }


        Issue issue = new Issue(issueRequest.getBookId(), issueRequest.getReaderId());
        issueRepository.save(issue);
        return issue;
    }
    public Issue getIssueById(long id){
        return issueRepository.getReferenceById(id);
    }
    public List<Issue> getAllIssues(){
        return issueRepository.findAll();
    }

    /**
     * Проверка, что у читателя максимально допустимое количество книг
     * @param id id читателя
     * @return
     */
    public boolean check(long id){
        int countOfBooks = 1;
        for(Issue issue : issueRepository.findAll()){
            if(issue.getIdReader() == id){
                countOfBooks++;
                if(countOfBooks > max_allowed_books){
                    return false;
                }
            }
        }
        return true;
    }
}