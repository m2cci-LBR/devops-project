package devops.services;

import devops.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import devops.repositories.IBookRepository;

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
