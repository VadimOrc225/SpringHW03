package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.entity.Issue;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private List<Book> bookList = new ArrayList<>();

    public BookRepository() {
        bookList.add(new Book("Война и мир"));
        bookList.add(new Book("Мастер и Маргарита"));
        bookList.add(new Book("Приключения Буратино"));
    }

    public Book getById(long id){
        return bookList.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }
    public void addBook(Book book){
        bookList.add(book);
    }
    public List<Book> getAllBooks(){
        return bookList;
    }
    public boolean deleteBook(Book book){
        return bookList.removeIf(b -> b.equals(book));
    }
}
