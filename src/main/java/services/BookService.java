package services;

import entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import repositories.IBookRepository;

import java.util.List;
import java.util.Optional;

public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public void createBook(Book b) {
          this.bookRepository.save(b);
    }

    @Override
    public void deleteBookById(long id) {
       this.bookRepository.deleteById(id);
    }
}
