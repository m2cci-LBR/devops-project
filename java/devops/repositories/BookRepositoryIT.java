package devops.repositories;

import devops.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)

//@DataJpaTest for integration test persistance layer
@DataJpaTest

// BookRepository integration test
public class BookRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IBookRepository bookRepository;

    @Test
    public  void FindBookByTitleTest(){

        // given
        Book book=new Book("RepositoryTest","g1","description...","author...","0","",null,0);

        entityManager.persist(book);
        entityManager.flush();

        // When
        Book found=bookRepository.findByTitle(book.getTitle());

        // Then
        assertThat(found).isEqualTo(book);

    }

}
