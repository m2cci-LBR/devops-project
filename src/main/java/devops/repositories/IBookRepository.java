package devops.repositories;

import devops.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book,Long> {
    public Book findByTitle(String title);
}
