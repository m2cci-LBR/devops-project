package services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import repositories.IBookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class BookServiceUnitTest {

    // Creation of our beans that will be injected (BookService in our case)

    @TestConfiguration
    static class BookServiceUnitTestContextConfiguration{
       @Bean
        public IBookService bookService(){
           return new BookService();
       }
    }

    // This is the service that will be tested
    @Autowired
    private IBookService bookService;

    // This is a mock for book repository
    @MockBean
    private IBookRepository bookRepository;

    private List<Book> books;

    @Before
    public void setUp(){
     Book b1=new Book("servicetest1","g1","description....","author....","0","",null,0);
     Book b2=new Book("servicetest2","g2","description....","author....","0","",null,0);
     b1.setId( (long) 1);
     b2.setId( (long) 2);
     this.books=new ArrayList<>(Arrays.asList(b1,b2));

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(b1));

        Mockito.doAnswer((Answer) invocation -> {
            this.books.add(invocation.getArgument(0));
            return null;
               })
                .when(bookRepository).save(any(Book.class));

        Mockito.doAnswer((Answer) invocation -> {
            this.books.removeIf(e -> e.getId() == invocation.getArgument(0));
               return null;
                        })
                      .when(bookRepository).deleteById(any(Long.class));
    }

    @Test
    public void getAllBooksTest(){
        //when
        List<Book> result=bookService.getAllBooks();

        //Then
        assertThat(result.size()).isEqualTo(2);

}



}
