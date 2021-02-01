package services;

import entities.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(long id);
    void createBook(Book b);
    void deleteBookById(long id);
}
